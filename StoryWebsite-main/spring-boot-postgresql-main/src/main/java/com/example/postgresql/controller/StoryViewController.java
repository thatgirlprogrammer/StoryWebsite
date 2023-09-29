package com.example.postgresql.controller;

import com.example.postgresql.model.DailyStatistics;
import com.example.postgresql.model.Story;
import com.example.postgresql.repository.DailyStatisticsRepository;
import com.example.postgresql.repository.StoryRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StoryViewController {
    @Autowired
    private final StoryRepository storyRepository;
    @Autowired
    private final DailyStatisticsRepository dailyStatiscsRepository;
    private int clicks = 0;

    public StoryViewController(StoryRepository storyRepository, DailyStatisticsRepository dailyStatiscsRepository) {
        this.storyRepository = storyRepository;
        this.dailyStatiscsRepository = dailyStatiscsRepository;
    }

    @RequestMapping(value="StoryView.html", method = RequestMethod.GET)
    public @ResponseBody ModelAndView geStory(@RequestParam("id") Long id) throws IOException {       
        String title = storyRepository.findById(id).get().getTitle();
        String body = storyRepository.findById(id).get().getBody();

        BufferedImage image = new BufferedImage(450, 600, BufferedImage.TYPE_INT_RGB);

        String[] words = body.split(" ");
        ArrayList<String[]> wordLsts = new ArrayList<String[]>();  
        for (int i = 0; i < words.length; i += 8) {
            String[] wordLst = {"", "", "", "", "", "", "", ""};
            for (int j = 0; j < wordLst.length; j++) {
                try { 
                    wordLst[j] = words[i+j];
                } catch (ArrayIndexOutOfBoundsException a) {

                }
            }
            wordLsts.add(wordLst);
        }

        // Write text to the image
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString(title, 10, 20);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        for (int i = 0; i < wordLsts.size(); i++) {
            String str = String.join(" ", wordLsts.get(i));
            g2d.drawString(str, 10, i * 20 + 40);
        }

        g2d.dispose();

        // Save the image as a png file
        ImageIO.write(image, "png", 
        new File("spring-boot-postgresql-main/src/main/resources/static/story.png"));
       
       ModelAndView mav = new ModelAndView("StoryView");
       Story story = storyRepository.findById(id).get();
       int index = getIndex(id);  
       DailyStatistics dailyStatistics;

       if (dailyStatiscsRepository.existsByDate(LocalDate.now()) && index != -1) {
           dailyStatistics = dailyStatiscsRepository.findByDate(LocalDate.now()).get(index);
       } else {
           dailyStatistics = new DailyStatistics(id);
       }

       dailyStatistics.setViews(dailyStatistics.getViews() + 1);
       dailyStatistics.setDownloads(dailyStatistics.getDownloads() + clicks);
       dailyStatiscsRepository.save(dailyStatistics);
       
       int views = 0;
       int downloads = 0;
       for (int i = 0; i < dailyStatiscsRepository.findByStoryIdOrderByDateAsc(id).size(); i++) {
            views += dailyStatiscsRepository.findByStoryIdOrderByDateAsc(id).get(i).getViews();
            downloads += dailyStatiscsRepository.findByStoryIdOrderByDateAsc(id).get(i).getDownloads();
       }
       
       mav.addObject("story", storyRepository.findById(id).get());
       mav.addObject("daily_statistics", dailyStatiscsRepository.findByStoryIdOrderByDateAsc(id));
       mav.addObject("views", views);
       mav.addObject("downloads", downloads);
       clicks = 0;
       return mav;
   }

   private int getIndex(Long id) {
       ArrayList<DailyStatistics> stats = dailyStatiscsRepository.findByDate(LocalDate.now());
       ArrayList<Long> ids = new ArrayList<Long>();
       for (DailyStatistics s : stats) {
           ids.add(s.getStoryId());
       }

       return  ids.indexOf(id);
   }

   @GetMapping("/download")
   public void download(HttpServletResponse response) throws IOException {
       // Get the file to download
       File file = new File("spring-boot-postgresql-main/src/main/resources/static/story.png");

       // Set the content type of the response
       response.setContentType("application/octet-stream");

       // Set the content length of the response
       response.setContentLength((int) file.length());

       // Set the download filename
       response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

       // Write the file to the response
       Files.copy(file.toPath(), response.getOutputStream());
       clicks++;
   }
}
