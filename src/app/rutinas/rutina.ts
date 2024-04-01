import { Ejercicio } from '../ejercicios/ejercicio';

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
