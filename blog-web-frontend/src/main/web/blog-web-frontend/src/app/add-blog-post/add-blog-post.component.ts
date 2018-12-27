import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BlogPost } from '../model/blog-post';
import { BlogPostsService } from '../service/blog-posts-service.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessage } from '../model/error-message';
import { ErrorMessageService } from '../service/error-message-service';

@Component({
  selector: 'blog-app-add-blog-post',
  templateUrl: './add-blog-post.component.html',
  styleUrls: ['./add-blog-post.component.css']
})
export class AddBlogPostComponent implements OnInit {

  post: BlogPost;

  blogPostForm: FormGroup;

  submitted = false;

  @Output()
  blogPostsListChanged = new EventEmitter<boolean>();

  @Output()
  errorChanged = new EventEmitter<ErrorMessage>();

  constructor(
    private modalService: NgbModal,
    private blogPostsService: BlogPostsService,
    private formBuilder: FormBuilder,
    private errorMessageService: ErrorMessageService
  ) { }

  ngOnInit() {
    this.blogPostForm = this.formBuilder.group({
      blogPostTitle: ['', Validators.required],
      blogPostContent: ['', Validators.required]
    }, {});
  }

  // convenience getter for easy access to form fields
  get form() { return this.blogPostForm.controls; }

  open(content) {
    this.submitted = false;
    this.post = new BlogPost();
    this.modalService
    .open(content, {ariaLabelledBy: 'modal-basic-title'})
    .result
    .then((formData) => {
      this.createPost(formData);
    }, () => {});
  }

  submit(modal) {
    this.submitted = true;
    if (!this.blogPostForm.invalid) {
      modal.close(this.blogPostForm.value);
    }
  }

  private createPost(result) {
    const newPost = new BlogPost();
    newPost.title = result.blogPostTitle;
    newPost.content = result.blogPostContent;

    this.blogPostsService.createPost(newPost)
    .subscribe(createResponse => {
      console.log(createResponse);
      this.blogPostsListChanged.emit(true);
    }, error => {
      this.errorMessageService.emitErrorMessage(error, this.errorChanged);
    });
  }
}
