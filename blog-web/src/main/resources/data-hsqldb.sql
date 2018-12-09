DROP TABLE post IF EXISTS;

CREATE TABLE post (
  id VARCHAR(50) NOT NULL,
  title VARCHAR(50) NOT NULL,
  content VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO post(id, title, content) VALUES ('1', 'Sample post 1 title','Sample post 1 content');
INSERT INTO post(id, title, content) VALUES ('2', 'Sample post 2 title','Sample post 2 content');
INSERT INTO post(id, title, content) VALUES ('3', 'Sample post 3 title','Sample post 3 content');