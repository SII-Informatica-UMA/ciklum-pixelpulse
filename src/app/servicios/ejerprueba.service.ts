import { Injectable } from '@angular/core';
import { Ejercicio } from '../entities/ejercicio';

@Injectable({
  providedIn: 'root'
})
export class EjerpruebaService {
  public ejercicios: Ejercicio []=[
    { id: 1,
      nombre: "Cinta",
      descripcion: "Corrermucho",
      observaciones: "No te caigas",
      tipo: "Cardio",
      musculosTrabajados: "Corazon",
      material: "Cinta de correr",
      dificultad: "Facil",
      multimedia: ["string[]"],
},
    {
      id: 2,
          nombre: "Flexiones",
          descripcion: "parribapabajo",
          observaciones: "ponte recto",
          tipo: "Fuerza",
          musculosTrabajados: "Pecho",
          material: "Suelo",
          dificultad: "Facil",
          multimedia: ["string[]"],
    }
  ,{
      id: 3,
      nombre: "Flexiones de pecho",
      descripcion: "Ejercicio para fortalecer el pecho y los brazos.",
      observaciones: "Realizar con la espalda recta y los codos cerca del cuerpo.",
      tipo: "Fuerza",
      musculosTrabajados: "Pectorales, tríceps, deltoides",
      material: "Ninguno",
      dificultad: "Intermedia",
      multimedia: ["imagen2.jpg", "video2.mp4"]
    }
  ];
  ejercicioSeleccionado?:   Ejercicio;
  constructor() { }

  getEjercicio(): Ejercicio [] {
    return this.ejercicios;
  }

  addEjercicio(ejercicio: Ejercicio) {
    ejercicio.id = Math.max(...this.ejercicios.map(c => c.id)) + 1;
    this.ejercicios.push(ejercicio);
  }

  editarEjercicio(contacto: Ejercicio) {
    let indice = this.ejercicios.findIndex(c => c.id == contacto.id);
    this.ejercicios[indice] = contacto;
  }
  seleccionarEjercicio(ejercicio: Ejercicio){
    this.ejercicioSeleccionado= ejercicio;
    
  }
  eliminarEjercicio(id: number) {
    let indice = this.ejercicios.findIndex(c => c.id == id);
    this.ejercicios.splice(indice, 1);
  }

}