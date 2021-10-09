jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOpcode, Opcode } from '../opcode.model';
import { OpcodeService } from '../service/opcode.service';

import { OpcodeRoutingResolveService } from './opcode-routing-resolve.service';

describe('Service Tests', () => {
  describe('Opcode routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OpcodeRoutingResolveService;
    let service: OpcodeService;
    let resultOpcode: IOpcode | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OpcodeRoutingResolveService);
      service = TestBed.inject(OpcodeService);
      resultOpcode = undefined;
    });

    describe('resolve', () => {
      it('should return IOpcode returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOpcode = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOpcode).toEqual({ id: 123 });
      });

      it('should return new IOpcode if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOpcode = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultOpcode).toEqual(new Opcode());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Opcode })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOpcode = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOpcode).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
