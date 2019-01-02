import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BlogPostsService } from '../service/blog-posts-service.service';
import { ErrorMessage } from '../model/error-message';
import { ErrorMessageService } from '../service/error-message-service';
import { ModificationType } from "../model/modification-type";

@Component({
  selector: 'blog-app-delete-blog-post',
  templateUrl: './delete-blog-post.component.html',
  styleUrls: ['./delete-blog-post.component.scss']
})
export class DeleteBlogPostComponent {

  @Input()
  postId: number;

  @Output()
  blogPostsListChanged = new EventEmitter<ModificationType>();

  @Output()
  errorChanged = new EventEmitter<ErrorMessage>();

  constructor(
    private modalService: NgbModal,
    private blogPostsService: BlogPostsService,
    private errorMessageService: ErrorMessageService
  ) { }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'})
    .result
    .then(() => {
      this.removePost();
    }, () => {});
  }

  private removePost() {
    this.blogPostsService.deletePost(this.postId)
    .subscribe(() => {
      this.blogPostsListChanged.emit(ModificationType.Delete);
    }, error => {
      this.errorMessageService.emitErrorMessage(error, this.errorChanged);
    });
  }
}
