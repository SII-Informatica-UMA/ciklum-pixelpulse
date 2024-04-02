import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoRutinaComponent } from './listado-rutinas.component';

describe('ListadoRutinasComponent', () => {
  let component: ListadoRutinaComponent;
  let fixture: ComponentFixture<ListadoRutinaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoRutinaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListadoRutinaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
