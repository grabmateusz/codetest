package com.pierceecom.blog.dto;

import com.pierceecom.blog.dto.validation.ExistingPostGroup;
import com.pierceecom.blog.dto.validation.NewPostGroup;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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

  @Null(groups = NewPostGroup.class)
  @XmlElement(name = "id")
  private String id;

  @NotNull(groups = { NewPostGroup.class, ExistingPostGroup.class })
  @XmlElement(name = "title")
  private String title;

  @NotNull(groups = { NewPostGroup.class, ExistingPostGroup.class })
  @XmlElement(name = "content")
  private String content;
}