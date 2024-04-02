import { Component } from '@angular/core';
import { Ejercicio, EjercicioImpl } from '../entities/ejercicio';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-formulario-ejercicio',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-ejercicios.component.html',
  styleUrls: ['./formulario-ejercicios.component.css']
})
export class FormularioEjercicioComponent {
  accion?: "Añadir" | "Editar";
  _ejercicio: Ejercicio = new EjercicioImpl();
  error: string = '';

  constructor(public modal: NgbActiveModal) { }

  get ejercicio () {
    return this._ejercicio;
  }

  set ejercicio(e: Ejercicio) {
    this._ejercicio = e;
  }

  guardarEjercicio(): void {
    // Cerrar el modal y pasar el objeto de ejercicio modificado
    this.modal.close(this.ejercicio);
  }
}
