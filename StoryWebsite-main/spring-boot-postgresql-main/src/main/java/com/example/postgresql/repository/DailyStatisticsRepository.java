package com.example.postgresql.repository;

import com.example.postgresql.model.DailyStatistics;
import com.example.postgresql.model.ViewsAndDownloads;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStatisticsRepository extends JpaRepository<DailyStatistics, Long> {
   public ArrayList<DailyStatistics> findAll();
   public ArrayList<DailyStatistics> findByStoryIdOrderByDateAsc(long storyId);
   public ArrayList<DailyStatistics> findByDate(LocalDate date);
   boolean existsByDate(LocalDate date);
   boolean existsByStoryId(long storyId);

   @Query("SELECT SUM(d.views) FROM daily_statistics d GROUP BY d.storyId ORDER BY d.storyId ASC")
   ArrayList<Long> sumViewsPerStory();
   @Query("SELECT SUM(d.downloads) FROM daily_statistics d GROUP BY d.storyId ORDER BY d.storyId ASC")
   ArrayList<Long> sumDownloadsPerStory();
}
