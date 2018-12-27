import { Component, OnInit } from '@angular/core';
import { BlogPost } from '../model/blog-post';
import { BlogPostsService } from '../service/blog-posts-service.service';
import { ErrorMessage } from '../model/error-message';

@Component({
  selector: 'blog-app-list-blog-post',
  templateUrl: './list-blog-post.component.html',
  styleUrls: ['./list-blog-post.component.css']
})
export class ListBlogPostComponent implements OnInit {

  error: ErrorMessage;

  blogPosts: BlogPost[];

  constructor(private blogPostsService: BlogPostsService) {
    this.blogPosts = [];
  }

  getPosts() {
    const blogPosts = [];
    this.blogPostsService.getPosts()
    .subscribe(
      response => {
        response.forEach(post => blogPosts.push(post))
        this.blogPosts = blogPosts;
      }, error => this.error = error);
  }

  ngOnInit() {
    this.getPosts();
  }

  onBlogPostsListChanged(event) {
    this.error = null;
    this.getPosts();
  }

  onErrorChanged(event) {
    this.error = event;
  }

  noBlogPosts(): boolean {
    return this.blogPosts.length === 0;
  }
}
