import { Component } from '@angular/core';
import { Rutina } from '../entities/rutina';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-formulario-rutinas',
  templateUrl: './formulario-rutinas.component.html',
  styleUrls: ['./formulario-rutinas.component.css']
})
export class FormularioRutinaComponent {
  accion?: "Añadir" | "Editar";
  rutina: Rutina = {
    nombre: '',
    descripcion: '',
    observaciones: '',
    ejercicios: []
  };
  error: string = '';

  constructor(public modal: NgbActiveModal) { }

  guardarRutina(): void {
    // Aquí puedes agregar validaciones necesarias antes de cerrar el modal
    this.modal.close(this.rutina);
  }
  
}
