import { Component } from '@angular/core';
import { Rutina, RutinaImpl } from '../entities/rutina';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Ejercicio } from '../entities/ejercicio';
import { EjercicioService } from '../servicios/ejercicio.service';

@Component({
  selector: 'app-formulario-rutinas',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-rutinas.component.html',
  styleUrls: ['./formulario-rutinas.component.css']
})
export class FormularioRutinaComponent {
  accion?: "Añadir" | "Editar";
  _rutina: Rutina = new RutinaImpl();
  error: string = '';
  ejercicios: Ejercicio[] = [];


  constructor(public modal: NgbActiveModal,private ejercicioService: EjercicioService,private modalService: NgbModal) { }
  get rutina () {
    return this._rutina;
  }
  ngOnInit(): void {
    this.cargarEjercicios();
  }
  abrirFormulario(): void {
    const modalRef = this.modalService.open(FormularioRutinaComponent);
    modalRef.componentInstance.accion = 'Añadir';
    modalRef.componentInstance.ejercicios = this.ejercicios; // Pasa los ejercicios al formulario
    modalRef.result.then((result) => {
      // Manejar el resultado si es necesario
    }, (reason) => {
      // Manejar el cierre del modal si es necesario
    });
  }
  cargarEjercicios(): void {
    this.ejercicioService.getEjercicios().subscribe(ejercicios => {
      this.ejercicios = ejercicios;
    });
  }
  set rutina(r: Rutina) {
    this._rutina = r;
  }
  guardarRutina(): void {
    // Aquí puedes agregar validaciones necesarias antes de cerrar el modal
    this.modal.close(this.rutina);
  }
  
}
