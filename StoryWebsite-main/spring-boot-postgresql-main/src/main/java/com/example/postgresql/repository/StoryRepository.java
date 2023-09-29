package com.example.postgresql.repository;

import com.example.postgresql.model.Story;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    public ArrayList<Story> findAll();
    public ArrayList<Story> findByStoryId(long StoryId);
}
