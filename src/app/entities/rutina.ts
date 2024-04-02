import { Ejercicio } from './ejercicio';

export interface Rutina {
  nombre: string;
  descripcion: string;
  observaciones: string;
  ejercicios: {
    series: number;
    repeticiones: number;
    duracionMinutos: number;
    ejercicio: Ejercicio;
  }[];
}

export class RutinaImpl implements Rutina{
    nombre: string;
    descripcion: string;
    observaciones: string;
    ejercicios: {
    series: number;
    repeticiones: number;
    duracionMinutos: number;
    ejercicio: Ejercicio;
  }[];
  
  constructor(){
    this.nombre='';
    this.descripcion='';
    this.observaciones='';
    this.ejercicios=[];
}
}

