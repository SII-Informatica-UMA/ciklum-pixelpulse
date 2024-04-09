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

  constructor(private backend: BackendService, private usuarioService: UsuariosService) {}

  getEjercicios(): Observable<Ejercicio[]> {
    return this.usuarioService.getUsuarioId().pipe(
      switchMap((entrenadorId) => {
        return this.backend.getEjercicios(entrenadorId);
      })
    );
  }

  editarEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
    return this.usuarioService.getUsuarioId().pipe(
      switchMap((entrenadorId) => {
        return this.backend.putEjercicio(ejercicio.id, ejercicio,entrenadorId);
      })
    );
  }

  eliminarEjercicio(id: number): Observable<void> {
    return this.usuarioService.getUsuarioId().pipe(
      switchMap((entrenadorId) => {
        return this.backend.deleteEjercicio(id,entrenadorId);
      })
    );
  }

  anadirEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
    return this.usuarioService.getUsuarioId().pipe(
      switchMap((entrenadorId) => {
        return this.backend.postEjercicio( ejercicio,entrenadorId);
      })
    );
  }

  getEjercicio(idEjercicio: number): Observable<Ejercicio> {
    return this.usuarioService.getUsuarioId().pipe(
      switchMap((entrenadorId) => {
        return this.backend.getEjercicio(idEjercicio,entrenadorId);
      })
    );
  }
}
