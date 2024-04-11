import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioEjercicioComponent } from './formulario-ejercicios.component';

describe('FormularioEjercicioComponent', () => {
  let component: FormularioEjercicioComponent;
  let fixture: ComponentFixture<FormularioEjercicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioEjercicioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FormularioEjercicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
