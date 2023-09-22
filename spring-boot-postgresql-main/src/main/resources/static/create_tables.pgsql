
Drop table daily_statistics;
drop table story;


Create table Story (
    id SERIAL PRIMARY key,
    title varchar(255),
    body varchar(100000),
    views int,
    downloads int,
    date_created date
    );




Create table daily_statistics (
    id INT,
    story_id INT ,
    views int,
    downloads int,
    date_recorded DATE,
 PRIMARY KEY(id),
   CONSTRAINT fk_story
      FOREIGN KEY(story_id) 
	  REFERENCES Story(id)
);
