package com.pierceecom.blog.controllers;

import com.github.dozermapper.core.Mapper;
import com.pierceecom.blog.domain.Post;
import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.services.PostsService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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

  @Autowired
  private Mapper mapper;

  @GetMapping("/posts")
  public List<PostDto> getAllPosts() {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts endpoint", requestId);

    List<PostDto> posts = postsService
        .findAll()
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());

    log.debug("{}: Returning Posts: {}", requestId, posts);

    return posts;
  }

  @GetMapping("/posts/{postId}")
  public PostDto getPostById(@PathVariable String postId) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts/{} endpoint", requestId, postId);

    Optional<PostDto> post = Optional.of(postsService)
                  .map(p -> p.getOne(postId))
                  .map(this::mapToDto);

    PostDto postDto = post.get();

    log.debug("{}: Returning Post: {}", requestId, postDto);
    return postDto;
  }

  @PostMapping("/posts")
  public PostDto addPost(@RequestBody PostDto post) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling POST request on /posts endpoint with argument: {}", requestId, post);

    Post postToAdd = mapToDomain(post);
    Post newlyCreatedPost = postsService.save(postToAdd);
    PostDto newlyCreatedPostDto = mapToDto(newlyCreatedPost);

    log.debug("{}: Returning persisted Post: {}", newlyCreatedPostDto);

    return newlyCreatedPostDto;
  }

  @PutMapping("/posts/{postId}")
  public PostDto updatePost(@PathVariable String postId, @RequestBody PostDto post) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling PUT request on /posts/{} endpoint with argument: {}", requestId, postId, post);

    Post postToUpdate = mapToDomain(post);
    Post updatedPost = postsService.save(postToUpdate);
    PostDto updatedPostDto = mapToDto(updatedPost);

    log.debug("{}: Returning updated Post: {}", updatedPostDto);

    return updatedPostDto;
  }

  @DeleteMapping("/posts/{postId}")
  public void deletePostById(@PathVariable String postId) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling DELETE request on /posts/{} endpoint", requestId, postId);

    postsService.deleteById(postId);

    log.debug("{}: Removed post with ID: {}", postId);

  }

  private PostDto mapToDto(Post p) {
    return mapper.map(p, PostDto.class);
  }

  private Post mapToDomain(PostDto p) {
    return mapper.map(p, Post.class);
  }
}
