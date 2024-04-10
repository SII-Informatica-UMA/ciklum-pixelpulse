import { Ejercicio } from './ejercicio';

export interface Rutina {
  id: number;
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
    id: number;
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
    this.id=0;
    this.nombre='';
    this.descripcion='';
    this.observaciones='';
    this.ejercicios=[];
}
}

