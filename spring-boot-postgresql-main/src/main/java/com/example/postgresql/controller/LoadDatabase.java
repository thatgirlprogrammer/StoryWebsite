package com.example.postgresql.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.postgresql.repository.DailyStatisticsRepository;
import com.example.postgresql.repository.StoryRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(StoryRepository repository, DailyStatisticsRepository dailyStatiscsRepository) {

    return args -> {
      log.info("Preloading ");
    };
  }
}
