import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import { BlogAppRoutingModule } from './blog-app-routing.module';
import { BlogAppComponent } from './blog-app.component';
import { AddBlogPostComponent } from './add-blog-post/add-blog-post.component';
import { EditBlogPostComponent } from './edit-blog-post/edit-blog-post.component';
import { ListBlogPostComponent } from './list-blog-post/list-blog-post.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    BlogAppComponent,
    AddBlogPostComponent,
    EditBlogPostComponent,
    ListBlogPostComponent
  ],
  imports: [
    BrowserModule,
    BlogAppRoutingModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [BlogAppComponent]
})
export class BlogAppModule { }
