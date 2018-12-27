import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { BlogAppComponent } from './blog-app.component';

describe('BlogAppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        BlogAppComponent
      ],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(BlogAppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'blog-web-webapp'`, () => {
    const fixture = TestBed.createComponent(BlogAppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('blog-web-webapp');
  });

  it('should render title in a h1 tag', () => {
    const fixture = TestBed.createComponent(BlogAppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to blog-web-webapp!');
  });
});
