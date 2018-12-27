import { Component, EventEmitter, OnInit, Output} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BlogPost } from '../model/blog-post';
import { BlogPostsService } from '../service/blog-posts-service.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorMessage } from '../model/error-message';
import { ErrorMessageService } from '../service/error-message-service';
import { ModificationType } from "../model/modification-type";

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

  initEditor() {
    const tinyMce = window.tinyMCE;
    if (tinyMce && tinyMce.activeEditor) {
      tinyMce.activeEditor.setContent('');
    }
  }

  private createPost(result) {
    const newPost = new BlogPost();
    newPost.title = result.blogPostTitle;
    newPost.content = result.blogPostContent;

    this.blogPostsService.createPost(newPost)
    .subscribe(() => {
      this.blogPostsListChanged.emit(ModificationType.Add);
    }, error => {
      this.errorMessageService.emitErrorMessage(error, this.errorChanged);
    });
  }
}
