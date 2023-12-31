package com.example.postgresql.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "story")
public class Story {
	private @Id @GeneratedValue long storyId;
	@Column(name = "body")
	private String body;
	@Column(name = "date_created")
	private LocalDate dateCreated;
	@Column(name = "title")
	private String title;

    public Story() {
    }

    public Story(String title, String body) {
		this.title = title;
		this.body = body;
		this.dateCreated = LocalDate.now();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getStoryId() {
		return storyId;
	}

	public void setStoryId(long storyId) {
		this.storyId = storyId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
}
