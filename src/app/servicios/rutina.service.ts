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

getRutina(id: number): Observable<Rutina> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.getRutina(id, entrenadorId.toString());
}

editarRutina(rutina: Rutina): Observable<Rutina> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.putRutina(rutina.id, rutina, entrenadorId.toString());
}

eliminarRutina(id: number): Observable<void> {
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.deleteRutina(id, entrenadorId.toString());
}

getRutinas(): Observable<Rutina[]> { 
  let entrenadorId = this.usuarioService.getUsuarioActualId();
  if (entrenadorId === null) {
    throw new Error('No se pudo obtener el ID del usuario');
  }
  return this.backend.getRutinas(entrenadorId.toString());
}

}
