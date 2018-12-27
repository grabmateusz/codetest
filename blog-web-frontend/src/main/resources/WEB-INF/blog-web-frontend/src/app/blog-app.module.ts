import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BlogAppRoutingModule } from './blog-app-routing.module';
import { BlogAppComponent } from './blog-app.component';
import { AddBlogPostComponent } from './add-blog-post/add-blog-post.component';
import { EditBlogPostComponent } from './edit-blog-post/edit-blog-post.component';
import { ListBlogPostComponent } from './list-blog-post/list-blog-post.component';
import { DeleteBlogPostComponent } from './delete-blog-post/delete-blog-post.component';

@NgModule({
  declarations: [
    BlogAppComponent,
    AddBlogPostComponent,
    EditBlogPostComponent,
    ListBlogPostComponent,
    DeleteBlogPostComponent
  ],
  imports: [
    AngularFontAwesomeModule,
    NgbModule,
    BrowserModule,
    BlogAppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})
  ],
  providers: [],
  bootstrap: [BlogAppComponent]
})
export class BlogAppModule { }
