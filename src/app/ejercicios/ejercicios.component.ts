import { Component, OnInit } from '@angular/core';
import { Ejercicio } from './ejercicio';
import { EjercicioService } from '../servicios/ejercicio.service';

@Component({
  selector: 'app-ejercicios',
  standalone: true,
  imports: [],
  templateUrl: './ejercicios.component.html',
  styleUrl: './ejercicios.component.css'
})
export class EjerciciosComponent implements OnInit {
  ejercicios: Ejercicio[] = [];

  constructor(private ejercicioService: EjercicioService) { }

  ngOnInit(): void {
    this.getEjercicios();
  }

  getEjercicios(): void {
    this.ejercicioService.getEjercicios()
      .subscribe(ejercicios => this.ejercicios = ejercicios);
  }
}
