package com.pierceecom.blog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@XmlRootElement(name = "Page")
@ApiModel("Page")
public class PageDto {

  @ApiModelProperty(name = "number", dataType = "string", value = "Number of a page")
  @XmlElement(name = "number")
  private Integer number;

  @ApiModelProperty(name = "numberOfElements", dataType = "string", value = "Number of elements on current page")
  @XmlElement(name = "numberOfElements")
  private Integer numberOfElements;

  @ApiModelProperty(name = "totalElements", dataType = "integer", value = "Number of all elements available")
  @XmlElement(name = "totalElements")
  private Integer totalElements;

  @ApiModelProperty(name = "size", dataType = "integer", value = "Size of a page")
  @XmlElement(name = "size")
  private Integer size;

  @ApiModelProperty(name = "totalPages", dataType = "integer", value = "Number of all pages available")
  @XmlElement(name = "totalPages")
  private Integer totalPages;
}
