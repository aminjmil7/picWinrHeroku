import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CycleService } from '../service/cycle.service';

import { CycleComponent } from './cycle.component';

describe('Component Tests', () => {
  describe('Cycle Management Component', () => {
    let comp: CycleComponent;
    let fixture: ComponentFixture<CycleComponent>;
    let service: CycleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CycleComponent],
      })
        .overrideTemplate(CycleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CycleComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CycleService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cycles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
