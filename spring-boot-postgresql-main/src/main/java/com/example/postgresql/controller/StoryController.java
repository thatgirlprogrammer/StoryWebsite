package com.example.postgresql.controller;

import com.example.postgresql.model.DailyStatistics;
import com.example.postgresql.model.Story;
import com.example.postgresql.repository.DailyStatisticsRepository;
import com.example.postgresql.repository.StoryRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StoryController {
    @Autowired
    private final StoryRepository storyRepository;
    @Autowired
    private final DailyStatisticsRepository dailyStatiscsRepository;
    private int clicks = 0;

    public StoryController(StoryRepository storyRepository, DailyStatisticsRepository dailyStatiscsRepository) {
        this.storyRepository = storyRepository;
        this.dailyStatiscsRepository = dailyStatiscsRepository;
    }

    @GetMapping("/")
    public ModelAndView getAllStories() {
        ModelAndView mav = new ModelAndView("home");
		mav.addObject("story", storyRepository.findAll());
		return mav;
    }

    @RequestMapping(value = "/story/submit", method = RequestMethod.POST)
    public String submitStory(@RequestParam("title") String title, @RequestParam("body") String body) {
        // Create a new story object
        Story story = new Story(title, body);
        // Save the story to the database
        storyRepository.save(story);
        DailyStatistics dailyStatistics = new DailyStatistics(story.getId());
        dailyStatiscsRepository.save(dailyStatistics);

        // Redirect the user to the home page
        return "redirect:/";
    }

    @GetMapping("/NewStory.html") 
    public String newStory(Model model) { 
        model.addAttribute("story", new Story()); 
        return "NewStory.html"; 
    }


     @RequestMapping(value="StoryView.html", method = RequestMethod.GET)
     public @ResponseBody ModelAndView geStory(@RequestParam("id") Long id) throws IOException {       
        String title = storyRepository.findById(id).get().getTitle();
        String body = storyRepository.findById(id).get().getBody();
        FileWriter writer = new FileWriter("/Applications/spring-boot-postgresql-main/src/main/resources/templates/story.txt");
        
        writer.write(title + "\n" + body);
        writer.close();
        
        ModelAndView mav = new ModelAndView("StoryView");
        Story story = storyRepository.findById(id).get();
        story.setViews(story.getViews() + 1);
        int index = getIndex(id);  
        DailyStatistics dailyStatistics;

        if (dailyStatiscsRepository.existsByDate(LocalDate.now()) && index != -1) {
            dailyStatistics = dailyStatiscsRepository.findByDate(LocalDate.now()).get(index);
        } else {
            dailyStatistics = new DailyStatistics(id);
        }

        dailyStatistics.setViews(dailyStatistics.getViews() + 1);
        story.setDownloads(story.getDownloads() + clicks);
        dailyStatistics.setDownloads(dailyStatistics.getDownloads() + clicks);
        storyRepository.save(story);
        dailyStatiscsRepository.save(dailyStatistics);
		mav.addObject("story", storyRepository.findById(id).get());
        mav.addObject("daily_statistics", dailyStatiscsRepository.findByStoryIdOrderByDateAsc(id));
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
        File file = new File("src/main/resources/templates/story.txt");

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
