import { Component } from '@angular/core';
import { Rutina, RutinaImpl } from '../entities/rutina';
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
  _rutina: Rutina = new RutinaImpl();
  error: string = '';

  constructor(public modal: NgbActiveModal) { }
  get rutina () {
    return this._rutina;
  }

  set rutina(r: Rutina) {
    this._rutina = r;
  }
  guardarRutina(): void {
    // Aquí puedes agregar validaciones necesarias antes de cerrar el modal
    this.modal.close(this.rutina);
  }
  
}
