import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BlogPost } from '../model/blog-post';
import { BlogPostsService } from '../service/blog-posts-service.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessage } from '../model/error-message';
import { ErrorMessageService } from '../service/error-message-service';
import { ModificationType } from "../model/modification-type";

@Component({
  selector: 'blog-app-edit-blog-post',
  templateUrl: './edit-blog-post.component.html',
  styleUrls: ['./edit-blog-post.component.scss']
})
export class EditBlogPostComponent implements OnInit {

  post: BlogPost;

  blogPostForm: FormGroup;

  submitted: boolean;

  @Input()
  postId: number;

  @Output()
  blogPostsListChanged = new EventEmitter<ModificationType>();

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

  get form() { return this.blogPostForm.controls; }

  open(content) {
    this.blogPostsService.getPostById(this.postId).subscribe(response => {
      this.post = response;
      this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'})
      .result
      .then((formData) => {
        this.updatePost(formData);
      }, () => {});
    }, error => {
      this.errorMessageService.emitErrorMessage(error, this.errorChanged);
    });
  }

  submit(modal) {
    this.submitted = true;
    if (!this.blogPostForm.invalid) {
      modal.close(this.blogPostForm.value);
    }
  }

  private updatePost(formData) {
    const updatedPost = new BlogPost(formData.blogPostTitle, formData.blogPostContent, this.postId);
    this.blogPostsService.updatePost(updatedPost)
    .subscribe(() => {
      this.blogPostsListChanged.emit(ModificationType.Update);
    }, error => {
      this.errorMessageService.emitErrorMessage(error, this.errorChanged);
    });
  }
}
