import { ApplicationRef, Component, OnInit } from '@angular/core';
import { BlogPost } from '../model/blog-post';
import { BlogPostsService } from '../service/blog-posts-service.service';
import { ErrorMessage } from '../model/error-message';
import { ModificationType } from "../model/modification-type";

@Component({
  selector: 'blog-app-list-blog-post',
  templateUrl: './list-blog-post.component.html',
  styleUrls: ['./list-blog-post.component.scss']
})
export class ListBlogPostComponent implements OnInit {

  FIRST_PAGE: number = 1;

  page: number = this.FIRST_PAGE;

  previousPage: number = this.page;

  pageSize: number;

  error: ErrorMessage;

  blogPosts: BlogPost[] = [];

  totalPages: number;

  totalElements: number;

  loaded: boolean;

  changeSource: ChangeSource;

  lastModification: ModificationType;

  lastPageRequested: number;

  constructor(
    private blogPostsService: BlogPostsService,
    private app: ApplicationRef
  ) {
  }

  getPosts() {
    const blogPosts = [];
    this.blogPostsService.getPosts(this.page, this.pageSize)
    .subscribe(
      response => {
        this.handleResponse(response, blogPosts);
      }, error => this.error = error);
  }

  private handleResponse(response, blogPosts) {
    const previousPages = this.totalPages;
    this.totalElements = response.page.totalElements;
    this.totalPages = response.page.totalPages;
    this.pageSize = response.page.size;

    response.posts.forEach(post => blogPosts.push(post))
    this.blogPosts = blogPosts;
    if (previousPages != this.totalPages) {
      this.forcePaginationRefresh();
    }
  }

  ngOnInit() {
    this.getPosts();
  }

  onBlogPostsListChanged(event: ModificationType) {
    this.error = null;
    this.changeSource = ChangeSource.Data;
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
    const pageChange = this.isPageChanged();
    const addChange = this.isAddChange();
    const newPageRequired = this.isNewPageRequired(addChange);
    const previousPageRequired = this.isPreviousPageRequired();

    this.handlePageChange(newPageRequired, addChange, previousPageRequired);
    let shouldFetch = this.shouldFetchPosts(newPageRequired);

    if ((newPageRequired || previousPageRequired || pageChange || force) && shouldFetch) {
      this.lastPageRequested = this.page;
      this.getPosts();
    }
    this.previousPage = this.page;
  }

  private shouldFetchPosts(newPageRequired) {
    let shouldRequest = true;
    if (newPageRequired && this.lastPageRequested == this.page && this.page !== this.FIRST_PAGE) {
      shouldRequest = false;
    }
    return shouldRequest;
  }

  private isPreviousPageRequired() {
    return ModificationType.Delete == this.lastModification
      && this.changeSource == ChangeSource.Data
      && this.totalElements == (this.pageSize * (this.totalPages - 1)) + 1
      && this.page == this.totalPages
      && this.page > 1;
  }

  private handlePageChange(newPageRequired, addChange, previousPageRequired) {
    if (newPageRequired) {
      this.page = parseInt('' + this.totalPages) + parseInt('' + 1);
    } else if (addChange) {
      this.page = parseInt('' + this.totalPages);
    }
    if (previousPageRequired) {
      this.page = this.totalPages - 1;
    }
  }

  private isNewPageRequired(addChange) {
    return addChange
      && this.totalElements == this.pageSize * this.totalPages;
  }

  private isAddChange() {
    return ModificationType.Add == this.lastModification
      && this.changeSource == ChangeSource.Data;
  }

  private isPageChanged() {
    return this.page != this.previousPage;
  }

  onClick() {
    this.changeSource = ChangeSource.Click;
  }

  private forcePaginationRefresh() {
    this.loaded = true;
    this.page--;
    this.app.tick();
    this.page++;
    this.app.tick();
  }
}

enum ChangeSource {
  Click,
  Data
}
