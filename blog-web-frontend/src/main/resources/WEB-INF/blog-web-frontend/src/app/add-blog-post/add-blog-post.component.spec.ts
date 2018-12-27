import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogAddBlogPostComponent } from './add-blog-post.component';

describe('BlogAddBlogPostComponent', () => {
  let component: BlogAddBlogPostComponent;
  let fixture: ComponentFixture<BlogAddBlogPostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlogAddBlogPostComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlogAddBlogPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
