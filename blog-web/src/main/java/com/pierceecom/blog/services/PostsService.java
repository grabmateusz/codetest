package com.pierceecom.blog.services;

import com.pierceecom.blog.domain.Post;
import com.pierceecom.blog.exceptions.PostNotFoundException;
import com.pierceecom.blog.repositories.PostsRepository;
import java.util.List;
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

  public Post getOne(Long id) throws PostNotFoundException {
    return postsRepository
        .findById(id)
        .orElseThrow(() -> new PostNotFoundException(id));
  }

  @Transactional
  public Post create(Post post) {
    return postsRepository.save(post);
  }

  @Transactional
  public Post update(Post updatedPost) throws PostNotFoundException {
    getOne(updatedPost.getId());
    return postsRepository.save(updatedPost);
  }

  @Transactional
  public void deleteById(Long postId) throws PostNotFoundException {
    getOne(postId);
    postsRepository.deleteById(postId);
  }
}
