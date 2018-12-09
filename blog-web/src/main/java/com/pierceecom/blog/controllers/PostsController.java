package com.pierceecom.blog.controllers;

import com.pierceecom.blog.domain.Post;
import com.pierceecom.blog.services.PostsService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostsController {

  @Autowired
  private PostsService postsService;

  @GetMapping("/posts")
  public List<Post> getAllPosts() {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts endpoint", requestId);

    List<Post> posts = postsService.findAll();

    log.debug("{}: Returning Posts: {}", requestId, posts);
    return posts;
  }

  @GetMapping("/posts/{postId}")
  public Post getPostById(@PathVariable String postId) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts/{} endpoint", requestId, postId);

    Post post = postsService.getOne(postId);

    log.debug("{}: Returning Post: {}", requestId, post);
    return post;
  }

  @PostMapping("/posts")
  public Post addPost(@RequestBody Post post) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling POST request on /posts endpoint with argument: {}", requestId, post);

    Post newlyCreatedPost = postsService.save(post);

    log.debug("{}: Returning persisted Post: {}", newlyCreatedPost);

    return newlyCreatedPost;
  }

  @PutMapping("/posts/{postId}")
  public Post updatePost(@PathVariable String postId, @RequestBody Post post) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling PUT request on /posts/{} endpoint with argument: {}", requestId, postId, post);

    Post updatedPost = postsService.save(post);

    log.debug("{}: Returning updated Post: {}", updatedPost);

    return updatedPost;
  }

  @DeleteMapping("/posts/{postId}")
  public void deletePostById(@PathVariable String postId) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling DELETE request on /posts/{} endpoint", requestId, postId);

    postsService.deleteById(postId);

    log.debug("{}: Removed post with ID: {}", postId);

  }
}
