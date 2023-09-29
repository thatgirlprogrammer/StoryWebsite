package com.example.postgresql.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "daily_statistics")
@Table(name = "daily_statistics")
public class DailyStatistics {
	private @Id @GeneratedValue  long daily_statistics_id;
	@Column(name = "views")
	private int views;
	@Column(name = "downloads")
	private int downloads;
	@Column(name = "date_recorded")
	private LocalDate date;
	@Column(name = "story_id")
	private long storyId;

    public DailyStatistics() {
    }

    public DailyStatistics(long story_id) {
        views = 0;
        downloads = 0;
        this.storyId = story_id;
        this.date = LocalDate.now();
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public long getStoryId() {
        return storyId;
    }

    public void setStoryId(long story_id) {
        this.storyId = story_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toString() {
		return "StoryId: " + storyId + " Views: " + views + " Downloads: " 
        + downloads + "date: " + date.toString();
	}

}
