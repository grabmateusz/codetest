package com.pierceecom.blog.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pierceecom.blog.Application;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicationIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getAllPosts_json() throws Exception {
    String expectedResponse = getResource("getAllPostsNoData.json");
    mockMvc.perform(get("/posts")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertOne_json() throws Exception {
    String newPost1 = getResource("post1.json");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String expectedResponse = getResource("getAllPostsWith1Post.json");
    mockMvc.perform(get("/posts")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertTwo_json() throws Exception {
    String newPost1 = getResource("post1.json");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.json");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String expectedResponse = getResource("getAllPostsWith2Posts.json");
    mockMvc.perform(get("/posts")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertThree_json() throws Exception {
    String newPost1 = getResource("post1.json");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.json");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String newPost3 = getResource("post3.json");
    mockMvc.perform(post("/posts")
        .content(newPost3)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/3"));

    String expectedResponse = getResource("getAllPostsWith3Posts.json");
    mockMvc.perform(get("/posts")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertThreeDeleteSecond_json() throws Exception {
    String newPost1 = getResource("post1.json");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.json");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String newPost3 = getResource("post3.json");
    mockMvc.perform(post("/posts")
        .content(newPost3)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/3"));

    mockMvc.perform(delete("/posts/3")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk());

    String expectedResponse = getResource("getAllPostsWith2Posts.json");
    mockMvc.perform(get("/posts")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertOneAndUpdateFirst_json() throws Exception {
    String newPost1 = getResource("post1.json");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String updatedPost1 = getResource("post1Updated.json");
    mockMvc.perform(put("/posts/1")
        .content(updatedPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated());

    String expectedResponse = getResource("getAllPostsWith1PostFirstUpdated.json");
    mockMvc.perform(get("/posts")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertThreeUpdateFirst_json() throws Exception {
    String newPost1 = getResource("post1.json");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.json");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String newPost3 = getResource("post3.json");
    mockMvc.perform(post("/posts")
        .content(newPost3)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/3"));

    String updatedPost1 = getResource("post1Updated.json");
    mockMvc.perform(put("/posts/1")
        .content(updatedPost1)
        .headers(getJsonHeaders()))
        .andExpect(status().isCreated());

    String expectedResponse = getResource("getAllPostsWith3PostsFirstUpdated.json");
    mockMvc.perform(get("/posts")
        .headers(getJsonHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void getAllPosts_xml() throws Exception {
    String expectedResponse = getResource("getAllPostsNoData.xml");
    mockMvc.perform(get("/posts")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertOne_xml() throws Exception {
    String newPost1 = getResource("post1.xml");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String expectedResponse = getResource("getAllPostsWith1Post.xml");
    mockMvc.perform(get("/posts")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertTwo_xml() throws Exception {
    String newPost1 = getResource("post1.xml");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.xml");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String expectedResponse = getResource("getAllPostsWith2Posts.xml");
    mockMvc.perform(get("/posts")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertThree_xml() throws Exception {
    String newPost1 = getResource("post1.xml");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.xml");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String newPost3 = getResource("post3.xml");
    mockMvc.perform(post("/posts")
        .content(newPost3)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/3"));

    String expectedResponse = getResource("getAllPostsWith3Posts.xml");
    mockMvc.perform(get("/posts")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertThreeDeleteSecond_xml() throws Exception {
    String newPost1 = getResource("post1.xml");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.xml");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String newPost3 = getResource("post3.xml");
    mockMvc.perform(post("/posts")
        .content(newPost3)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/3"));

    mockMvc.perform(delete("/posts/3")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk());

    String expectedResponse = getResource("getAllPostsWith2Posts.xml");
    mockMvc.perform(get("/posts")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertOneAndUpdateFirst_xml() throws Exception {
    String newPost1 = getResource("post1.xml");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String updatedPost1 = getResource("post1Updated.xml");
    mockMvc.perform(put("/posts/1")
        .content(updatedPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated());

    String expectedResponse = getResource("getAllPostsWith1PostFirstUpdated.xml");
    mockMvc.perform(get("/posts")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  @Test
  public void insertThreeUpdateFirst_xml() throws Exception {
    String newPost1 = getResource("post1.xml");
    mockMvc.perform(post("/posts")
        .content(newPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/1"));

    String newPost2 = getResource("post2.xml");
    mockMvc.perform(post("/posts")
        .content(newPost2)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/2"));

    String newPost3 = getResource("post3.xml");
    mockMvc.perform(post("/posts")
        .content(newPost3)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/posts/3"));

    String updatedPost1 = getResource("post1Updated.xml");
    mockMvc.perform(put("/posts/1")
        .content(updatedPost1)
        .headers(getXmlHeaders()))
        .andExpect(status().isCreated());

    String expectedResponse = getResource("getAllPostsWith3PostsFirstUpdated.xml");
    mockMvc.perform(get("/posts")
        .headers(getXmlHeaders()))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResponse));
  }

  private HttpHeaders getJsonHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    return httpHeaders;
  }

  private HttpHeaders getXmlHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_XML);
    httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
    return httpHeaders;
  }

  private String getResource(String fileName) throws IOException {
    InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
    return IOUtils.readLines(resourceAsStream, StandardCharsets.UTF_8).
        stream()
        .collect(Collectors.joining());
  }
}
