import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteBlogPostComponent } from './delete-blog-post.component';

describe('DeleteBlogPostComponent', () => {
  let component: DeleteBlogPostComponent;
  let fixture: ComponentFixture<DeleteBlogPostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteBlogPostComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteBlogPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
