DROP TABLE post IF EXISTS;

CREATE TABLE post (
  id VARCHAR(50) NOT NULL,
  title VARCHAR(50) NOT NULL,
  content VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

