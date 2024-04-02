import { Component, OnInit } from '@angular/core';
import { RutinaService } from '../servicios/rutina.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { Rutina, RutinaImpl } from '../entities/rutina';
import { FormularioRutinaComponent } from '../formulario-rutinas/formulario-rutinas.component';

@Component({
  selector: 'app-listado-rutina',
  templateUrl: './listado-rutinas.component.html',
  styleUrls: ['./listado-rutinas.component.css']
})
export class ListadoRutinaComponent implements OnInit {
  rutinas: Rutina[] = [];

  constructor(private rutinasService: RutinaService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.actualizarRutinas();
  }

  actualizarRutinas() {
    this.rutinasService.getRutinas().subscribe(rutinas => {
      this.rutinas = rutinas;
    });
  }

  añadirRutina(): void {
    let ref = this.modalService.open(FormularioRutinaComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.rutina = new RutinaImpl();
    ref.result.then((rutina: Rutina) => {
      this.rutinasService.añadirRutina(rutina).subscribe(() => {
        this.actualizarRutinas();
      });
    }, () => {});
  }
  

  private rutinaEditada(rutina: Rutina): void {
    this.rutinasService.editarRutina(rutina).subscribe(() => {
      this.actualizarRutinas();
    });
  }

  eliminarRutina(nombre: string): void {
    this.rutinasService.eliminarRutina(nombre).subscribe(() => {
      this.actualizarRutinas();
    });
  }

  editarRutina(rutina: Rutina): void {
    let ref = this.modalService.open(FormularioRutinaComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.rutina = { ...rutina };
    ref.result.then((rutina: Rutina) => {
      this.rutinaEditada(rutina);
    }, () => {});
  }
}
