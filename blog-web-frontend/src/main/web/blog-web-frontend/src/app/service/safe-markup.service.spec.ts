import { TestBed } from '@angular/core/testing';

import { SafeMarkupService } from './safe-markup.service';

describe('SafeMarkupService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SafeMarkupService = TestBed.get(SafeMarkupService);
    expect(service).toBeTruthy();
  });
});
