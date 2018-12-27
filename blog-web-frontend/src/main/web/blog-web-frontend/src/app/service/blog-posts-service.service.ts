import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BlogPost } from '../model/blog-post';
import { BlogPostsPage } from "../model/blog-posts-page";
import { environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BlogPostsService {

  baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = environment.apiUrl + '/posts';
  }

  getPosts(page: number, pageSize: number) {
    const pageToRequest = (page > 0) ? page - 1 : page;
    return this.http.get<BlogPostsPage>(`${this.baseUrl}?page=${pageToRequest}&size=${pageSize}`);
  }

  getPostById(id: number) {
    return this.http.get<BlogPost>(this.baseUrl + '/' + id);
  }

  createPost(post: BlogPost) {
    return this.http.post(this.baseUrl, post);
  }

  updatePost(post: BlogPost) {
    return this.http.put(this.baseUrl + '/' + post.id, post);
  }

  deletePost(id: number) {
    return this.http.delete(this.baseUrl + '/' + id);
  }
}
