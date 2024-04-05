import { Component, OnInit } from '@angular/core';
import { EjercicioService } from '../servicios/ejercicio.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Ejercicio, EjercicioImpl } from '../entities/ejercicio';
import { FormularioEjercicioComponent } from '../formulario-ejercicios/formulario-ejercicios.component';

@Component({
  selector: 'app-listado-ejercicio',
  standalone: true,
  imports: [CommonModule,RouterOutlet,RouterLink,RouterLinkActive],
  templateUrl: './listado-ejercicios.component.html',
  styleUrls: ['./listado-ejercicios.component.css']
})
export class ListadoEjercicioComponent implements OnInit {
  ejercicios: Ejercicio[] = [];

  constructor(private ejercicioService: EjercicioService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.actualizarEjercicios();
  }

  actualizarEjercicios() {
    this.ejercicioService.getEjercicios().subscribe(ejercicios => {
      this.ejercicios = ejercicios;
    });
  }

  anadirEjercicio(): void {
    let ref = this.modalService.open(FormularioEjercicioComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.ejercicio = new EjercicioImpl();
    ref.result.then((ejercicio: Ejercicio) => {
      this.ejercicioService.anadirEjercicio(ejercicio).subscribe(() => {
        this.actualizarEjercicios();
      });
    }, () => {});
  }

  private ejercicioEditado(ejercicio: Ejercicio): void {
    this.ejercicioService.editarEjercicio(ejercicio).subscribe(() => {
      this.actualizarEjercicios();
    });
  }

  eliminarEjercicio(id: number): void {
    this.ejercicioService.eliminarEjercicio(id).subscribe(() => {
      this.actualizarEjercicios();
    });
  }

  editarEjercicio(ejercicio: Ejercicio): void {
    let ref = this.modalService.open(FormularioEjercicioComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.ejercicio = { ...ejercicio };
    ref.result.then((ejercicio: Ejercicio) => {
      this.ejercicioEditado(ejercicio);
    }, () => {});
  }
}
