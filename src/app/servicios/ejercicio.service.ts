import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Ejercicio } from "../entities/ejercicio";
import { BackendFakeService } from "./backend.fake.service";
import { BackendService } from "./backend.service";
import { UsuariosService } from "../services/usuarios.service";
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EjercicioService {

  constructor(private backend: BackendFakeService, private usuarioService: UsuariosService) {}

  getEjercicios(): Observable<Ejercicio[]> {
    let entrenadorId = this.usuarioService.getUsuarioActualId();
    if (entrenadorId === null) {
      throw new Error('No se pudo obtener el ID del usuario');
    }
    return this.backend.getEjercicios(entrenadorId.toString());
  }
  

  editarEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
    let entrenadorId = this.usuarioService.getUsuarioActualId();
    if (entrenadorId === null) {
      throw new Error('No se pudo obtener el ID del usuario');
    }
    return this.backend.putEjercicio(ejercicio.id,ejercicio, entrenadorId.toString());
  }
  

  eliminarEjercicio(id: number): Observable<void> {
    let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.deleteEjercicio(id, entrenadorId.toString());
}

  anadirEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.postEjercicio(ejercicio, entrenadorId.toString());
}

getEjercicio(idEjercicio: number): Observable<Ejercicio> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.getEjercicio(idEjercicio, entrenadorId.toString());
}
}
