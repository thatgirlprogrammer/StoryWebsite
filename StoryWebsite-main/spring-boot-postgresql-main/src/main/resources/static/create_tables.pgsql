
Drop table daily_statistics;
drop table story;


Create table Story (
    story_idid SERIAL PRIMARY key,
    title varchar(255),
    body varchar(100000),
    views int,
    downloads int,
    date_created date
    );




Create table daily_statistics (
    daily_statistics_id SERIAL PRIMARY KEY,
    story_id INT ,
    date_recorded DATE,
 PRIMARY KEY(id),
   CONSTRAINT fk_story
      FOREIGN KEY(story_id) 
	  REFERENCES Story(story_id)
);
