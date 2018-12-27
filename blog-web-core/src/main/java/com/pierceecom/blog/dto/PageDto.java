package com.pierceecom.blog.dto;

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
public class PageDto {

  @XmlElement(name = "number")
  private String number;

  @XmlElement(name = "numberOfElements")
  private String numberOfElements;

  @XmlElement(name = "totalElements")
  private String totalElements;

  @XmlElement(name = "size")
  private String size;

  @XmlElement(name = "totalPages")
  private String totalPages;
}
