package com.pierceecom.blog.exceptions;

public class PostNotFoundException extends Exception {

  public PostNotFoundException(Long postId) {
    super("Could not find post with id " + postId);
  }
}
