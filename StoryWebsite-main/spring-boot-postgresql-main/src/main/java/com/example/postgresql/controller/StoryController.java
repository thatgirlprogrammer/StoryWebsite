package com.example.postgresql.controller;

import com.example.postgresql.model.DailyStatistics;
import com.example.postgresql.model.Story;
import com.example.postgresql.model.ViewsAndDownloads;
import com.example.postgresql.repository.DailyStatisticsRepository;
import com.example.postgresql.repository.StoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StoryController {
    @Autowired
    private final StoryRepository storyRepository;
    @Autowired
    private final DailyStatisticsRepository dailyStatiscsRepository;

    public StoryController(StoryRepository storyRepository, DailyStatisticsRepository dailyStatiscsRepository) {
        this.storyRepository = storyRepository;
        this.dailyStatiscsRepository = dailyStatiscsRepository;
    }

    @GetMapping("/")
    public ModelAndView getAllStories() {
        ModelAndView mav = new ModelAndView("home");
        ArrayList<Story> stories = storyRepository.findAll();
        List<Long> views = dailyStatiscsRepository.sumViewsPerStory();
        List<Long> downloads = dailyStatiscsRepository.sumDownloadsPerStory();
        sort(stories);

		mav.addObject("story", stories);
        mav.addObject("views", views);
        mav.addObject("downloads", downloads);
        return mav;
    }

    private void sort(ArrayList<Story> list) {
        list.sort((o1, o2)
                  -> o1.getDateCreated().compareTo(
                      o2.getDateCreated()));
    }

    @RequestMapping("/images/grandTeton.jpg")
    public ResponseEntity<byte[]> getImage() throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get("spring-boot-postgresql-main/src/main/resources/static/images/grandTeton.jpg"));
        return ResponseEntity.ok(imageBytes);
    }
}
