import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOpcode, Opcode } from '../opcode.model';

import { OpcodeService } from './opcode.service';

describe('Service Tests', () => {
  describe('Opcode Service', () => {
    let service: OpcodeService;
    let httpMock: HttpTestingController;
    let elemDefault: IOpcode;
    let expectedResult: IOpcode | IOpcode[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(OpcodeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        opirationCode: 'AAAAAAA',
        count: 0,
        ceationDated: currentDate,
        expirationDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ceationDated: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Opcode', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ceationDated: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ceationDated: currentDate,
            expirationDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Opcode()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Opcode', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            opirationCode: 'BBBBBB',
            count: 1,
            ceationDated: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ceationDated: currentDate,
            expirationDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Opcode', () => {
        const patchObject = Object.assign(
          {
            opirationCode: 'BBBBBB',
            count: 1,
          },
          new Opcode()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ceationDated: currentDate,
            expirationDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Opcode', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            opirationCode: 'BBBBBB',
            count: 1,
            ceationDated: currentDate.format(DATE_TIME_FORMAT),
            expirationDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ceationDated: currentDate,
            expirationDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Opcode', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addOpcodeToCollectionIfMissing', () => {
        it('should add a Opcode to an empty array', () => {
          const opcode: IOpcode = { id: 123 };
          expectedResult = service.addOpcodeToCollectionIfMissing([], opcode);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(opcode);
        });

        it('should not add a Opcode to an array that contains it', () => {
          const opcode: IOpcode = { id: 123 };
          const opcodeCollection: IOpcode[] = [
            {
              ...opcode,
            },
            { id: 456 },
          ];
          expectedResult = service.addOpcodeToCollectionIfMissing(opcodeCollection, opcode);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Opcode to an array that doesn't contain it", () => {
          const opcode: IOpcode = { id: 123 };
          const opcodeCollection: IOpcode[] = [{ id: 456 }];
          expectedResult = service.addOpcodeToCollectionIfMissing(opcodeCollection, opcode);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(opcode);
        });

        it('should add only unique Opcode to an array', () => {
          const opcodeArray: IOpcode[] = [{ id: 123 }, { id: 456 }, { id: 52406 }];
          const opcodeCollection: IOpcode[] = [{ id: 123 }];
          expectedResult = service.addOpcodeToCollectionIfMissing(opcodeCollection, ...opcodeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const opcode: IOpcode = { id: 123 };
          const opcode2: IOpcode = { id: 456 };
          expectedResult = service.addOpcodeToCollectionIfMissing([], opcode, opcode2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(opcode);
          expect(expectedResult).toContain(opcode2);
        });

        it('should accept null and undefined values', () => {
          const opcode: IOpcode = { id: 123 };
          expectedResult = service.addOpcodeToCollectionIfMissing([], null, opcode, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(opcode);
        });

        it('should return initial array if no Opcode is added', () => {
          const opcodeCollection: IOpcode[] = [{ id: 123 }];
          expectedResult = service.addOpcodeToCollectionIfMissing(opcodeCollection, undefined, null);
          expect(expectedResult).toEqual(opcodeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
