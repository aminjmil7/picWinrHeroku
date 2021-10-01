import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOpcode } from '../opcode.model';

@Component({
  selector: 'jhi-opcode-detail',
  templateUrl: './opcode-detail.component.html',
})
export class OpcodeDetailComponent implements OnInit {
  opcode: IOpcode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opcode }) => {
      this.opcode = opcode;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
