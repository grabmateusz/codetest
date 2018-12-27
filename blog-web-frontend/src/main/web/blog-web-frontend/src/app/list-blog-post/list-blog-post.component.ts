import { ApplicationRef, Component, OnInit } from '@angular/core';
import { BlogPost } from '../model/blog-post';
import { BlogPostsService } from '../service/blog-posts-service.service';
import { ErrorMessage } from '../model/error-message';
import { ModificationType } from "../model/modification-type";

@Component({
  selector: 'blog-app-list-blog-post',
  templateUrl: './list-blog-post.component.html',
  styleUrls: ['./list-blog-post.component.css']
})
export class ListBlogPostComponent implements OnInit {

  error: ErrorMessage;

  blogPosts: BlogPost[];

  totalPages: number;

  totalElements: number;

  page: number;

  previousPage: number;

  pageSize: number;

  loaded: boolean;

  changeSource: string;

  lastModification: ModificationType;

  constructor(
    private blogPostsService: BlogPostsService,
    private app: ApplicationRef
  ) {
    this.blogPosts = [];
    this.totalElements = 0;
    this.page = 1;
    this.previousPage = this.page;
    this.pageSize = 2;
  }

  getPosts() {
    const blogPosts = [];
    this.blogPostsService.getPosts(this.page, this.pageSize)
    .subscribe(
      response => {
        this.totalElements = response.page.totalElements;
        this.totalPages = response.page.totalPages;
        response.posts.forEach(post => blogPosts.push(post))
        this.blogPosts = blogPosts;
        this.forcePaginationRefresh();
      }, error => this.error = error);
  }

  ngOnInit() {
    this.getPosts();
  }

  onBlogPostsListChanged(event: ModificationType) {
    this.error = null;
    this.changeSource = 'data';
    this.lastModification = event;
    this.handleChange(true);
  }

  onErrorChanged(event) {
    this.error = event;
  }

  noBlogPosts(): boolean {
    return this.blogPosts.length === 0;
  }

  onPageChange(event) {
    this.handleChange(false);
  }

  handleChange(force) {
    const pageChange = this.page != this.previousPage;

    const addChange = ModificationType.Add == this.lastModification
      && this.changeSource == 'data';
    const newPageRequired = addChange
      && this.totalElements == this.pageSize * this.totalPages;

    if (newPageRequired) {
      this.page = parseInt('' + this.totalPages) + parseInt(''+ 1);
    } else if (addChange) {
      this.page = parseInt('' + this.totalPages);
    }

    const previousPageRequired = ModificationType.Delete == this.lastModification
      && this.changeSource == 'data'
      && this.totalElements == (this.pageSize * (this.totalPages - 1)) + 1
      && this.page == this.totalPages;

    if (previousPageRequired) {
      this.page = this.totalPages - 1;
    }

    if (newPageRequired || previousPageRequired || pageChange || force) {
      this.getPosts();
    }

    this.previousPage = this.page;
  }

  onClick() {
    this.changeSource = 'click';
  }

  private forcePaginationRefresh() {
    this.loaded = true;
    this.page = this.page - 1;
    this.app.tick();
    this.page = this.page + 1;
    this.app.tick();
  }
}
