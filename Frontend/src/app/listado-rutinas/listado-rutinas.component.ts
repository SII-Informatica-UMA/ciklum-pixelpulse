import { Component, OnInit } from '@angular/core';
import { RutinaService } from '../servicios/rutina.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Rutina, RutinaImpl } from '../entities/rutina';
import { FormularioRutinaComponent } from '../formulario-rutinas/formulario-rutinas.component';

@Component({
  selector: 'app-listado-rutina',
  standalone: true,
  imports: [CommonModule,RouterOutlet,RouterLink,RouterLinkActive],
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

anadirRutina(): void {
  let ref = this.modalService.open(FormularioRutinaComponent);
  ref.componentInstance.accion = "Añadir";
  ref.componentInstance.rutina = new RutinaImpl(); // La rutina se inicializa vacía
  ref.result.then((rutina: Rutina) => {
    if (rutina) {
      this.rutinasService.añadirRutina(rutina).subscribe(() => this.actualizarRutinas());
    }
  });
}
  

  private rutinaEditada(rutina: Rutina): void {
    this.rutinasService.editarRutina(rutina).subscribe(() => {
      this.actualizarRutinas();
    });
  }

  eliminarRutina(id: number): void {
    this.rutinasService.eliminarRutina(id).subscribe(() => {
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
