import { TestBed } from '@angular/core/testing';

import { EjerpruebaService } from './ejerprueba.service';

describe('EjerpruebaService', () => {
  let service: EjerpruebaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EjerpruebaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
