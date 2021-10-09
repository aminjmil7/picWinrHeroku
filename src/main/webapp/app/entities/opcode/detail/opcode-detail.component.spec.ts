import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpcodeDetailComponent } from './opcode-detail.component';

describe('Component Tests', () => {
  describe('Opcode Management Detail Component', () => {
    let comp: OpcodeDetailComponent;
    let fixture: ComponentFixture<OpcodeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [OpcodeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ opcode: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(OpcodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpcodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load opcode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.opcode).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
