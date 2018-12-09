package com.pierceecom.blog.controllers;

import com.pierceecom.blog.domain.Post;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostsController {

  @GetMapping("/posts")
  public List<Post> getPosts() {
    UUID requestId = UUID.randomUUID();
    log.debug("{}: Calling GET request on /posts endpoint", requestId);
    //TODO: Receive all Posts from DB
    List<Post> posts = Collections.emptyList();
    log.debug("{}: Returning Posts: {}", requestId, posts);
    return posts;
  }

  @PostMapping("/posts")
  public Post addPost(Post post) {
    UUID requestId = UUID.randomUUID();
    log.debug("{}: Calling POST request on /posts endpoint with argument: {}", requestId, post);
    //TODO: Persist Post in DB
    log.debug("{}: Returning persisted Post: {}", post);
    return post;
  }

  @PutMapping("/posts/{postId}")
  public Post updatePost(@PathVariable String postId, Post post) {
    UUID requestId = UUID.randomUUID();
    log.debug("{}: Calling PUT request on /posts/{} endpoint with argument: {}", requestId, postId, post);
    //TODO: Persist Post in DB
    log.debug("{}: Returning persisted Post: {}", post);
    return post;
  }

  @DeleteMapping("/posts/{postId}")
  public void deletePost(@PathVariable String postId, Post post) {
    UUID requestId = UUID.randomUUID();
    log.debug("{}: Calling DELETE request on /posts/{} endpoint with argument: {}", requestId, postId, post);
    //TODO: Delete Post in DB
    log.debug("{}: Removed post: {}", post);
  }
}
