jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICycle, Cycle } from '../cycle.model';
import { CycleService } from '../service/cycle.service';

import { CycleRoutingResolveService } from './cycle-routing-resolve.service';

describe('Service Tests', () => {
  describe('Cycle routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CycleRoutingResolveService;
    let service: CycleService;
    let resultCycle: ICycle | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CycleRoutingResolveService);
      service = TestBed.inject(CycleService);
      resultCycle = undefined;
    });

    describe('resolve', () => {
      it('should return ICycle returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCycle = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCycle).toEqual({ id: 123 });
      });

      it('should return new ICycle if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCycle = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCycle).toEqual(new Cycle());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCycle = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCycle).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
