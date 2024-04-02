import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoEjercicioComponent } from './listado-ejercicios.component';

describe('ListadoEjercicioComponent', () => {
  let component: ListadoEjercicioComponent;
  let fixture: ComponentFixture<ListadoEjercicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoEjercicioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListadoEjercicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
