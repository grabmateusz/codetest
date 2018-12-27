import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBlogPostComponent } from './list-blog-post.component';

describe('ListBlogPostComponent', () => {
  let component: ListBlogPostComponent;
  let fixture: ComponentFixture<ListBlogPostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListBlogPostComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListBlogPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
