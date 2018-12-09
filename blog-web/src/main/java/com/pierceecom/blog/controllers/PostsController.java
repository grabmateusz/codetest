package com.pierceecom.blog.controllers;

import com.github.dozermapper.core.Mapper;
import com.pierceecom.blog.domain.Post;
import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.dto.PostsListDto;
import com.pierceecom.blog.services.PostsService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
public class PostsController {

  @Autowired
  private PostsService postsService;

  @Autowired
  private Mapper mapper;

  @Autowired
  private Validator validator;

  @GetMapping(value = "/posts", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<PostsListDto> getAllPosts() {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts endpoint", requestId);

    List<PostDto> posts = postsService
        .findAll()
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());

    PostsListDto postsListDto = new PostsListDto(posts);
    ResponseEntity<PostsListDto> response = ResponseEntity.ok(postsListDto);

    log.debug("{}: Returning Posts: {}", requestId, response);

    return response;
  }

  @GetMapping(value = "/posts/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<PostDto> getPostById(@PathVariable String postId) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts/{} endpoint", requestId, postId);

    Optional<PostDto> post = Optional.of(postsService)
                  .flatMap(p -> p.getOne(postId))
                  .map(this::mapToDto);

    final ResponseEntity<PostDto> response;
    if (post.isPresent()) {
      response = ResponseEntity.ok(post.get());
      log.debug("{}: Returning Post: {}", requestId, response);
    } else {
      response = ResponseEntity.noContent().build();
      log.debug("{}: Returning no content response {} for Post ID: {}", requestId, response, postId);
    }
    return response;
  }

  @PostMapping(value = "/posts", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<PostDto> addPost(@RequestBody PostDto post, UriComponentsBuilder uriComponentsBuilder) {
    UUID requestId = UUID.randomUUID();

    log.warn("{}: Calling POST request on /posts endpoint with argument: {}", requestId, post);

    Set<ConstraintViolation<PostDto>> violations = validator.validate(post);

    ResponseEntity<PostDto> result;
    if (!violations.isEmpty()) {
      result = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
      log.warn("{}: Returning Result: {}", requestId, result);
    } else {
      Post postToAdd = mapToDomain(post);
      Post newlyCreatedPost = postsService.save(postToAdd);
      PostDto newlyCreatedPostDto = mapToDto(newlyCreatedPost);

      result = ResponseEntity.created(uriComponentsBuilder.path("/posts/{id}").build(newlyCreatedPost.getId())).build();

      log.warn("{}: Returning persisted Post: {}", newlyCreatedPostDto);
    }
    return result;
  }

  @PutMapping(value = "/posts/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<PostDto> updatePost(@PathVariable String postId, @RequestBody PostDto post, UriComponentsBuilder uriComponentsBuilder) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling PUT request on /posts/{} endpoint with argument: {}", requestId, postId, post);

    Set<ConstraintViolation<PostDto>> violations = validator.validate(post);

    ResponseEntity<PostDto> result;
    if (!violations.isEmpty()) {
      result = ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
      log.debug("{}: Returning information invalid input was provided: {}", requestId, result);
    } else {
      Post postToUpdate = mapToDomain(post);
      Optional<Post> alreadyPersistedPost = postsService.getOne(postId);
      if (alreadyPersistedPost.isPresent()) {
        Post updatedPost = postsService.save(postToUpdate);
        PostDto updatedPostDto = mapToDto(updatedPost);
        result = ResponseEntity.created(uriComponentsBuilder.build(updatedPostDto.getId())).build();
        log.debug("{}: Returning updated Post: {}", requestId, result);
      } else {
        result = ResponseEntity.notFound().build();
        log.debug("{}: Returning information that post with given ID does not exist: {}", requestId, result);
      }
    }
    return result;
  }

  @DeleteMapping(value = "/posts/{postId}")
  public ResponseEntity<Void> deletePostById(@PathVariable String postId) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling DELETE request on /posts/{} endpoint", requestId, postId);

    ResponseEntity<Void> result;
    Optional<Post> post = postsService.getOne(postId);
    if (post.isPresent()) {
      postsService.deleteById(postId);
      result = ResponseEntity.ok().build();
      log.debug("{}: Removed post with ID: {}. Sending response: {}", requestId, postId, result);
    } else {
      result = ResponseEntity.notFound().build();
      log.debug("{}: Could not remove post with ID: {}, as it does not exist. Returning response: {}", requestId, postId, result);
    }
    return result;
  }

  private PostDto mapToDto(Post p) {
    return mapper.map(p, PostDto.class);
  }

  private Post mapToDomain(PostDto p) {
    return mapper.map(p, Post.class);
  }
}
