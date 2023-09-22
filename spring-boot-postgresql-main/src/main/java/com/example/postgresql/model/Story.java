package com.example.postgresql.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Story {
	private @Id @GeneratedValue  long id;
	@Column(name = "body")
	private String body;
	@Column(name = "views")
	private int views;
	@Column(name = "downloads")
	private int downloads;
	@Column(name = "date_created")
	private LocalDate dateCreated;
	@Column(name = "title")
	private String title;

    public Story() {
    }

    public Story(String title, String body) {
		this.title = title;
		this.views = 0;
		this.downloads = 0;
		this.body = body;
		this.dateCreated = LocalDate.now();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
}
