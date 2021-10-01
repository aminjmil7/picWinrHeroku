import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { OpcodeService } from '../service/opcode.service';

import { OpcodeComponent } from './opcode.component';

describe('Component Tests', () => {
  describe('Opcode Management Component', () => {
    let comp: OpcodeComponent;
    let fixture: ComponentFixture<OpcodeComponent>;
    let service: OpcodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OpcodeComponent],
      })
        .overrideTemplate(OpcodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpcodeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(OpcodeService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
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
      expect(comp.opcodes?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
