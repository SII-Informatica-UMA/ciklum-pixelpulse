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
  let nuevaRutina = new RutinaImpl();
  nuevaRutina.ejercicios = [{
    ejercicio: {
      id: 0,
      nombre: '',
      descripcion: '', 
      observaciones: '',
      tipo: '', 
      musculosTrabajados: '', 
      material: '',
      dificultad: '',
      multimedia: [], 
    },
    series: 0,
    repeticiones: 0, 
    duracionMinutos: 0, 
  }];
  ref.componentInstance.rutina = nuevaRutina;
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
