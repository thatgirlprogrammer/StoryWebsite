package com.example.postgresql.repository;

import com.example.postgresql.model.DailyStatistics;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStatisticsRepository extends JpaRepository<DailyStatistics, Long> {
   public ArrayList<DailyStatistics> findAll();
   public ArrayList<DailyStatistics> findByStoryIdOrderByDateAsc(long storyId);
   public ArrayList<DailyStatistics> findByDate(LocalDate date);
   boolean existsByDate(LocalDate date);
   boolean existsByStoryId(long storyId);
}
