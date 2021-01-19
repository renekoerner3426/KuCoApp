import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DecreeWindowComponent } from './decree-window.component';

describe('DecreeWindowComponent', () => {
  let component: DecreeWindowComponent;
  let fixture: ComponentFixture<DecreeWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DecreeWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DecreeWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
