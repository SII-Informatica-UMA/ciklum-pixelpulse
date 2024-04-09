import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Rutina } from "../entities/rutina";
import { BackendFakeService } from "./backend.fake.service";
import { BackendService } from "./backend.service";
import { UsuariosService } from "../services/usuarios.service";

@Injectable({
  providedIn: 'root'
})
export class RutinaService {

  constructor(private backend: BackendService,private usuarioService : UsuariosService) {}

 añadirRutina(rutina: Rutina): Observable<Rutina> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.postRutina(rutina, entrenadorId.toString());
}

getRutina(nombre: string): Observable<Rutina> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.getRutina(nombre, entrenadorId.toString());
}

editarRutina(rutina: Rutina): Observable<Rutina> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.putRutina(rutina.nombre, rutina, entrenadorId.toString());
}

eliminarRutina(nombre: string): Observable<void> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.deleteRutina(nombre, entrenadorId.toString());
}

getRutinas(): Observable<Rutina[]> { 
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.getRutinas(entrenadorId.toString());
}

}
