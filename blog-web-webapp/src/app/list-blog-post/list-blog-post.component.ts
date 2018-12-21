import { Component, OnInit } from '@angular/core';
import {BlogPost} from "../model/blog-post";
import {BlogPostsService} from "../blog-posts-service.service";

@Component({
  selector: 'app-list-blog-post',
  templateUrl: './list-blog-post.component.html',
  styleUrls: ['./list-blog-post.component.css']
})
export class ListBlogPostComponent implements OnInit {

  blog_posts:BlogPost[];

  constructor(private blogPostsService: BlogPostsService) {
    this.blog_posts = [];
  }

  getPosts() {
    this.blogPostsService.getPosts()
    .subscribe(
      response => response.forEach(post => this.blog_posts.push(post)),
      error => console.error(error));
  }

  ngOnInit() {
    this.getPosts();
  }

}
