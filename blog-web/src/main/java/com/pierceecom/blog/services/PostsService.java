package com.pierceecom.blog.services;

import com.pierceecom.blog.domain.Post;
import com.pierceecom.blog.repositories.PostsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsService {

  @Autowired
  private PostsRepository postsRepository;

  public List<Post> findAll() {
    return postsRepository.findAll();
  }

  public Optional<Post> getOne(String id) {
    return postsRepository.findById(id);
  }

  public Post save(Post post) {
    return postsRepository.save(post);
  }

  public void deleteById(String id) {
    postsRepository.deleteById(id);
  }
}
