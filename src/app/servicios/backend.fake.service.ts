import { Injectable } from "@angular/core";
import { Observable, of, throwError } from "rxjs";
import { Rutina } from "../entities/rutina";
import { Ejercicio } from "../entities/ejercicio";
import { from } from "rxjs";


// Este servicio imita al backend pero utiliza localStorage para almacenar los datos

const ejercicioC: Ejercicio [] = [{
    id: 1,
    nombre: "Ejercicio 1",
    descripcion: "Descripción del ejercicio 1",
    observaciones: "Observaciones sobre el ejercicio 1",
    tipo: "Tipo de ejercicio",
    musculosTrabajados: "Músculos trabajados",
    material: "Material necesario",
    dificultad: "Dificultad del ejercicio",
    multimedia: ["imagen1.jpg", "video1.mp4"]
  },
 {
    id: 2,
    nombre: "Flexiones de pecho",
    descripcion: "Ejercicio para fortalecer el pecho y los brazos.",
    observaciones: "Realizar con la espalda recta y los codos cerca del cuerpo.",
    tipo: "Fuerza",
    musculosTrabajados: "Pectorales, tríceps, deltoides",
    material: "Ninguno",
    dificultad: "Intermedia",
    multimedia: ["imagen2.jpg", "video2.mp4"]
},
];

const RutinasC: Rutina[] = [
    {
        id: 1,
        nombre: "Rutina 1",
        descripcion: "Descripción de la Rutina 1",
        observaciones: "Observaciones sobre la Rutina 1",
        ejercicios: [
            {
                series: 3,
                repeticiones: 10,
                duracionMinutos: 20,
                ejercicio: ejercicioC[0]
            },
            {
                series: 4,
                repeticiones: 12,
                duracionMinutos: 25,
                ejercicio: ejercicioC[1]
            }
        ]
    },
    {
        id: 2,
        nombre: "Rutina 2",
        descripcion: "Descripción de la Rutina 2",
        observaciones: "Observaciones sobre la Rutina 2",
        ejercicios: [
            {
                series: 2,
                repeticiones: 15,
                duracionMinutos: 30,
                ejercicio: ejercicioC[1]
            },
            {
                series: 3,
                repeticiones: 10,
                duracionMinutos: 20,
                ejercicio: ejercicioC[0]
            }
        ]
    }
];

@Injectable({
  providedIn: 'root'
})
export class BackendFakeService {
    private rutinas: Rutina[];
    private ejercicios: Ejercicio[];
  constructor() {
  let _ejercicios = localStorage.getItem('ejercicios');
  if(_ejercicios){
    this.ejercicios = JSON.parse(_ejercicios);
  }else{
    this.ejercicios=[...ejercicioC]
  }
    let _rutinas = localStorage.getItem('rutinas')
    if(_rutinas){
      this.rutinas = JSON.parse(_rutinas);
    }else{
      this.rutinas = [...RutinasC]
    }
  }

 postEjercicio(ejercicio: Ejercicio,usuarioId: string): Observable<Ejercicio> {
  ejercicio.id = this.ejercicios.length + 1;
  this.ejercicios.push(ejercicio);
  this.guardarEjerciciosEnLocalStorage();
  return of(ejercicio);
}

getEjercicios(usuarioId: string): Observable<Ejercicio[]> {
  return of(this.ejercicios);
}

putEjercicio(id: number,ejercicio: Ejercicio,usuarioId: string): Observable<Ejercicio> {
  const index = this.ejercicios.findIndex(e => e.id === ejercicio.id);
  if (index !== -1) {
    this.ejercicios[index] = ejercicio;
    this.guardarEjerciciosEnLocalStorage();
    return of(ejercicio);
  } else {
    return throwError(new Error('Ejercicio no encontrado'));
  }
}

deleteEjercicio(id: number,usuarioId: string): Observable<void> {
  const index = this.ejercicios.findIndex(e => e.id === id);
  if (index !== -1) {
    this.ejercicios.splice(index, 1);
    this.guardarEjerciciosEnLocalStorage();
    return of(undefined);
  } else {
    return throwError(new Error('Ejercicio no encontrado'));
  }
}
getEjercicio(id: number,usuarioId: string): Observable<Ejercicio> {
  const ejercicio = this.ejercicios.find(e => e.id === id);
  if (ejercicio) {
    return of(ejercicio);
  } else {
    return throwError(new Error('Ejercicio no encontrado'));
  }
}

private guardarEjerciciosEnLocalStorage() {
  localStorage.setItem('ejercicios', JSON.stringify(this.ejercicios));
}

  getRutinas(usuarioId: string): Observable<Rutina[]> {
    return of(this.rutinas);
  }
  getRutina(id: number,usuarioId: string): Observable<Rutina> {
    const rutina = this.rutinas.find(r => r.id === id);
    if (rutina) {
      return of(rutina);
    } else {
      return throwError(new Error('Ejercicio no encontrado'));
    }
  }
  postRutina(rutina: Rutina,usuarioId: string): Observable<Rutina> {
    this.rutinas.push(rutina); // Agregamos la nueva rutina al arreglo
    this.guardarRutinasEnLocalStorage();
    return of(rutina);
  }
  private guardarRutinasEnLocalStorage() {
    localStorage.setItem('rutinas', JSON.stringify(this.rutinas));
  }
  putRutina(id:number,rutina: Rutina,usuarioId: string): Observable<Rutina> {
    const index = this.rutinas.findIndex(r => r.id === rutina.id); // Buscamos la rutina por su id
    if (index !== -1) {
      this.rutinas[index] = rutina; // Actualizamos la rutina existente
      this.guardarRutinasEnLocalStorage();
      return of(rutina);
    }
    return new Observable(observer => observer.error('No se encontró la rutina'));
  }
  
  deleteRutina(id: number,usuarioId: string): Observable<void> {
    const index = this.rutinas.findIndex(r => r.id === id); // Buscamos la rutina por su id
    if (index !== -1) {
      this.rutinas.splice(index, 1); // Eliminamos la rutina del arreglo
      this.guardarRutinasEnLocalStorage();
      return of();
    }
    return new Observable(observer => observer.error('No se encontró la rutina'));
  }
  
}
