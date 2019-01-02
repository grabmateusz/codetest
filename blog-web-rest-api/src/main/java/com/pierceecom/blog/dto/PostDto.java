package com.pierceecom.blog.dto;

import com.pierceecom.blog.dto.validation.ExistingPostGroup;
import com.pierceecom.blog.dto.validation.NewPostGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
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
@ApiModel("Post")
public class PostDto {

  @ApiModelProperty(name = "id", dataType = "integer", value = "example: 1")
  @Null(groups = NewPostGroup.class)
  @NotNull(groups = ExistingPostGroup.class)
  @XmlElement(name = "id")
  private Integer id;

  @ApiModelProperty(name = "title", dataType = "string", value = "example: what I did today")
  @NotNull(groups = { NewPostGroup.class, ExistingPostGroup.class })
  @Size(max = 1000, groups = { NewPostGroup.class, ExistingPostGroup.class })
  @XmlElement(name = "title")
  private String title;

  @ApiModelProperty(name = "id", dataType = "string", value = "example: wrote a boring post")
  @NotNull(groups = { NewPostGroup.class, ExistingPostGroup.class })
  @Size(max = 100000, groups = { NewPostGroup.class, ExistingPostGroup.class })
  @XmlElement(name = "content")
  private String content;
}