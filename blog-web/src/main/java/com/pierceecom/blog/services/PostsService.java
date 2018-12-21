package com.pierceecom.blog.services;

import com.pierceecom.blog.domain.Post;
import com.pierceecom.blog.exceptions.PostNotFoundException;
import com.pierceecom.blog.repositories.PostsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostsService {

  @Autowired
  private PostsRepository postsRepository;

  public List<Post> findAll() {
    return postsRepository.findAll();
  }

  public Optional<Post> getOne(Long id) {
    return postsRepository.findById(id);
  }

  @Transactional
  public Post create(Post post) {
    return postsRepository.save(post);
  }

  @Transactional
  public Post update(Post updatedPost) throws PostNotFoundException {
    Long postId = updatedPost.getId();
    Optional<Post> post = getOne(postId);
    Post result;
    if (post.isPresent()) {
      result = postsRepository.save(updatedPost);
    } else {
      throw new PostNotFoundException(postId);
    }
    return result;
  }

  @Transactional
  public void deleteById(Long postId) throws PostNotFoundException {
    Optional<Post> post = getOne(postId);
    if (post.isPresent()) {
      postsRepository.deleteById(postId);
    } else {
      throw new PostNotFoundException(postId);
    }
  }
}
