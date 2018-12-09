package com.pierceecom.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;
}
