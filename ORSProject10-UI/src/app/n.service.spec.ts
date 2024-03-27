import { TestBed } from '@angular/core/testing';

import { NService } from './n.service';

describe('NService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NService = TestBed.get(NService);
    expect(service).toBeTruthy();
  });
});
