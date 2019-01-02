package com.pierceecom.blog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Results")
@ApiModel("Results")
public class ResultsDto {

  @ApiModelProperty(name = "posts", dataType = "Post", value = "List of posts")
  @XmlElementWrapper(name = "Posts")
  @XmlElement(name = "Post")
  private List<PostDto> posts;

  @ApiModelProperty(name = "page", dataType = "Page", value = "Pagination information")
  @XmlElement(name = "Page")
  private PageDto page;
}
