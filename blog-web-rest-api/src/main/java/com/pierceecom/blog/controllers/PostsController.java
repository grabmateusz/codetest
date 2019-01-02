package com.pierceecom.blog.controllers;

import com.github.dozermapper.core.Mapper;
import com.pierceecom.blog.domain.Post;
import com.pierceecom.blog.dto.PageDto;
import com.pierceecom.blog.dto.ResultsDto;
import com.pierceecom.blog.dto.validation.ExistingPostGroup;
import com.pierceecom.blog.dto.validation.NewPostGroup;
import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.exceptions.PostNotFoundException;
import com.pierceecom.blog.services.PostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@Api(value = "Posts", tags = "Posts")
public class PostsController {

  @Autowired
  private PostsService postsService;

  @Autowired
  private Mapper mapper;

  @Autowired
  private Validator validator;

  @ApiOperation(value = "Get all posts", notes = "Returns all posts")
  @ApiResponses({
      @ApiResponse(code = 200, response = ResultsDto.class, message = "Successful operation")
  })
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
          value = "Results page you want to retrieve (0..N)"),
      @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
          value = "Number of records per page")
  })
  @GetMapping(value = "/posts", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<ResultsDto> getAllPosts(@ApiIgnore Pageable page) {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts endpoint", requestId);

    Page<Post> postsPage = postsService
        .findAll(page);

    List<PostDto> posts = postsPage
        .stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());

    PageDto pageDto = mapper.map(postsPage, PageDto.class);
    ResultsDto postsListDto = new ResultsDto(posts, pageDto);
    ResponseEntity<ResultsDto> response = ResponseEntity.ok(postsListDto);

    log.debug("{}: Returning Posts: {}", requestId, response);

    return response;
  }

  @ApiOperation(value = "Add a new post")
  @ApiResponses({
      @ApiResponse(code = 201, response = PostDto.class, message = "Location of newly created post"),
      @ApiResponse(code = 400, message = "Invalid input")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = "/posts", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<PostDto> addPost(
      @ApiParam(name = "body", required = true, value = "Post object that needs to be added")
      @RequestBody PostDto post,
      UriComponentsBuilder uriComponentsBuilder) {
    UUID requestId = UUID.randomUUID();

    log.warn("{}: Calling POST request on /posts endpoint with argument: {}", requestId, post);

    Set<ConstraintViolation<PostDto>> violations = validator.validate(post, NewPostGroup.class);

    ResponseEntity<PostDto> result;
    if (!violations.isEmpty()) {
      result = ResponseEntity.badRequest().build();
      log.warn("{}: Returning Result: {}", requestId, result);
    } else {
      Post postToAdd = mapToDomain(post);
      Post newlyCreatedPost = postsService.create(postToAdd);
      result = ResponseEntity.created(uriComponentsBuilder.path("/posts/{id}").build(newlyCreatedPost.getId())).build();
      log.warn("{}: Returning persisted Post: {}", requestId, result);
    }
    return result;
  }

  @ApiOperation(value = "Updates a post")
  @ApiResponses({
      @ApiResponse(code = 200, response = PostDto.class, message = "Updated post"),
      @ApiResponse(code = 400, message = "Invalid input"),
      @ApiResponse(code = 404, message = "Post not found")
  })
  @ApiImplicitParam(name = "postId", value = "ID of post to update", required = true)
  @PutMapping(value = "/posts/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<PostDto> updatePost(
      @PathVariable String postId,
      @ApiParam(name = "body", required = true, value = "Post object that needs to be updated")
      @RequestBody PostDto post) throws PostNotFoundException {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling PUT request on /posts/{} endpoint with argument: {}", requestId, postId, post);

    Set<ConstraintViolation<PostDto>> violations = validator.validate(post, ExistingPostGroup.class);

    ResponseEntity<PostDto> result;
    if (!violations.isEmpty()) {
      result = ResponseEntity.badRequest().build();
      log.debug("{}: Returning information invalid input was provided: {}", requestId, result);
    } else {
      Post postToUpdate = mapToDomain(post);
      Post updatedPost = postsService.update(postToUpdate);
      PostDto updatedPostDto = mapToDto(updatedPost);
      result = ResponseEntity.ok(updatedPostDto);
      log.debug("{}: Returning updated Post: {}", requestId, result);
    }
    return result;
  }

  @ApiOperation(value = "Find post by ID")
  @ApiResponses({
      @ApiResponse(code = 200, response = PostDto.class, message = "Successful operation"),
      @ApiResponse(code = 404, message = "Post not found")
  })
  @ApiImplicitParam(name = "postId", value = "ID of post to return", required = true)
  @GetMapping(value = "/posts/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<PostDto> getPostById(@PathVariable Long postId)
      throws PostNotFoundException {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling GET request on /posts/{} endpoint", requestId, postId);

    Post post = postsService.getOne(postId);
    PostDto postDto = mapToDto(post);

    ResponseEntity<PostDto> response = ResponseEntity.ok(postDto);
    log.debug("{}: Returning Post: {}", requestId, response);

    return response;
  }

  @ApiOperation(value = "Deletes a post")
  @ApiResponses({
      @ApiResponse(code = 204, message = "Successful operation"),
      @ApiResponse(code = 404, message = "Post not found")
  })
  @ApiImplicitParam(name = "postId", value = "ID of post to delete", required = true)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/posts/{postId}")
  public ResponseEntity<Void> deletePostById(@PathVariable Long postId) throws PostNotFoundException {
    UUID requestId = UUID.randomUUID();

    log.debug("{}: Calling DELETE request on /posts/{} endpoint", requestId, postId);

    postsService.deleteById(postId);
    ResponseEntity<Void> result = ResponseEntity.ok().build();

    log.debug("{}: Removed post with ID: {}. Sending response: {}", requestId, postId, result);

    return result;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(PostNotFoundException.class)
  public void handlePostNotFound() {
    // Intentionally left empty
  }

  private PostDto mapToDto(Post p) {
    return mapper.map(p, PostDto.class);
  }

  private Post mapToDomain(PostDto p) {
    return mapper.map(p, Post.class);
  }
}
