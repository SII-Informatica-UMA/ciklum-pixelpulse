import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Rutina } from "../entities/rutina";
import { Ejercicio } from "../entities/ejercicio";
import { SECRET_JWT } from "../app.config";
import { from } from "rxjs";
//import * as jose from 'jose';
import { FRONTEND_URI } from "../app.config";

// Este servicio imita al backend pero utiliza localStorage para almacenar los datos

const ejercicio1: Ejercicio = {
    id: 1,
    nombre: "Ejercicio 1",
    descripcion: "Descripción del ejercicio 1",
    observaciones: "Observaciones sobre el ejercicio 1",
    tipo: "Tipo de ejercicio",
    musculosTrabajados: "Músculos trabajados",
    material: "Material necesario",
    dificultad: "Dificultad del ejercicio",
    multimedia: ["imagen1.jpg", "video1.mp4"]
};

const ejercicio2: Ejercicio = {
    id: 2,
    nombre: "Flexiones de pecho",
    descripcion: "Ejercicio para fortalecer el pecho y los brazos.",
    observaciones: "Realizar con la espalda recta y los codos cerca del cuerpo.",
    tipo: "Fuerza",
    musculosTrabajados: "Pectorales, tríceps, deltoides",
    material: "Ninguno",
    dificultad: "Intermedia",
    multimedia: ["imagen2.jpg", "video2.mp4"]
};

const RutinasC: Rutina[] = [
    {
        nombre: "Rutina 1",
        descripcion: "Descripción de la Rutina 1",
        observaciones: "Observaciones sobre la Rutina 1",
        ejercicios: [
            {
                series: 3,
                repeticiones: 10,
                duracionMinutos: 20,
                ejercicio: ejercicio1
            },
            {
                series: 4,
                repeticiones: 12,
                duracionMinutos: 25,
                ejercicio: ejercicio2
            }
        ]
    },
    {
        nombre: "Rutina 2",
        descripcion: "Descripción de la Rutina 2",
        observaciones: "Observaciones sobre la Rutina 2",
        ejercicios: [
            {
                series: 2,
                repeticiones: 15,
                duracionMinutos: 30,
                ejercicio: ejercicio2
            },
            {
                series: 3,
                repeticiones: 10,
                duracionMinutos: 20,
                ejercicio: ejercicio1
            }
        ]
    }
];

@Injectable({
  providedIn: 'root'
})
export class BackendFakeService {
    private rutinas: { [nombre: string]: Rutina };
    private ejercicios: Ejercicio[];
  constructor() {
    this.ejercicios = [];
    this.rutinas = {};
  }

  getEjercicios(): Observable<Ejercicio[]> {
    return of(this.ejercicios);
  }

  postEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
    ejercicio.id = this.ejercicios.length + 1;
    this.ejercicios.push(ejercicio);
    return of(ejercicio);
  }

  putEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
    const index = this.ejercicios.findIndex(e => e.id === ejercicio.id);
    if (index !== -1) {
      this.ejercicios[index] = ejercicio;
      return of(ejercicio);
    }
    return new Observable(observer => observer.error('No se encontró el ejercicio'));
  }

  deleteEjercicio(id: number): Observable<void> {
    const index = this.ejercicios.findIndex(e => e.id === id);
    if (index !== -1) {
      this.ejercicios.splice(index, 1);
      return of();
    }
    return new Observable(observer => observer.error('No se encontró el ejercicio'));
  }

  getRutinas(): Observable<Rutina[]> {
    return of(Object.values(this.rutinas));
  }

  postRutina(rutina: Rutina): Observable<Rutina> {
    this.rutinas[rutina.nombre] = rutina;
    return of(rutina);
  }

  putRutina(rutina: Rutina): Observable<Rutina> {
    if (this.rutinas[rutina.nombre]) {
      this.rutinas[rutina.nombre] = rutina;
      return of(rutina);
    }
    return new Observable(observer => observer.error('No se encontró la rutina'));
  }

  deleteRutina(nombre: string): Observable<void> {
    if (this.rutinas[nombre]) {
      delete this.rutinas[nombre];
      return of();
    }
    return new Observable(observer => observer.error('No se encontró la rutina'));
  }
}
