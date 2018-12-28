package com.pierceecom.blog.repositories;

import com.pierceecom.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {

}