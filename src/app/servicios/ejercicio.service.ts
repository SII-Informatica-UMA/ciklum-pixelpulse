import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Ejercicio } from "../entities/ejercicio";
import { BackendFakeService } from "./backend.fake.service";

@Injectable({
  providedIn: 'root'
})
export class EjercicioService {

  constructor(private backend: BackendFakeService) {}

  getEjercicios(): Observable<Ejercicio[]> {
    return this.backend.getEjercicios();
  }

  editarEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
    return this.backend.putEjercicio(ejercicio);
  }

  eliminarEjercicio(id: number): Observable<void> {
    return this.backend.deleteEjercicio(id);
  }

  añadirEjercicio(ejercicio: Ejercicio): Observable<Ejercicio> {
    return this.backend.postEjercicio(ejercicio);
  }
}
