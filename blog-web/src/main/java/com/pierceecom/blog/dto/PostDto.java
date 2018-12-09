package com.pierceecom.blog.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Post")
public class PostDto {

  @NotNull
  @XmlElement(name = "id")
  private String id;

  @NotNull
  @XmlElement(name = "title")
  private String title;

  @NotNull
  @XmlElement(name = "content")
  private String content;
}