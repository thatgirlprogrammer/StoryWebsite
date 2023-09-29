package com.example.postgresql.controller;

import com.example.postgresql.model.DailyStatistics;
import com.example.postgresql.model.Story;
import com.example.postgresql.repository.DailyStatisticsRepository;
import com.example.postgresql.repository.StoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewStoryController {
    @Autowired
    private final StoryRepository storyRepository;
    @Autowired
    private final DailyStatisticsRepository dailyStatiscsRepository;

    public NewStoryController(StoryRepository storyRepository, DailyStatisticsRepository dailyStatiscsRepository) {
        this.storyRepository = storyRepository;
        this.dailyStatiscsRepository = dailyStatiscsRepository;
    }

    @RequestMapping(value = "/story/submit", method = RequestMethod.POST)
    public String submitStory(@RequestParam("title") String title, @RequestParam("body") String body) {
        // Create a new story object
        Story story = new Story(title, body);
        // Save the story to the database
        storyRepository.save(story);
        DailyStatistics dailyStatistics = new DailyStatistics(story.getStoryId());
        dailyStatiscsRepository.save(dailyStatistics);

        // Redirect the user to the home page
        return "redirect:/";
    }

    @GetMapping("/NewStory.html") 
    public String newStory(Model model) { 
        model.addAttribute("story", new Story()); 
        return "NewStory.html"; 
    }
}
