export interface Ejercicio {
  id: number;
  nombre: string;
  descripcion: string;
  observaciones: string;
  tipo: string;
  musculosTrabajados: string;
  material: string;
  dificultad: string;
  multimedia: string[];
}

export class EjercicioImpl implements Ejercicio {
  id: number;
  nombre: string;
  descripcion: string;
  observaciones: string;
  tipo: string;
  musculosTrabajados: string;
  material: string;
  dificultad: string;
  multimedia: string[];

  constructor() {
    this.id = 0;
    this.nombre = '';
    this.descripcion = '';
    this.observaciones = '';
    this.tipo = '';
    this.musculosTrabajados = '';
    this.material = '';
    this.dificultad = '';
    this.multimedia = [];
  }
}
