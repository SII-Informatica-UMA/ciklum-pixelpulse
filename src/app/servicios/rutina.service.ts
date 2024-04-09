import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Rutina } from "../entities/rutina";
import { BackendFakeService } from "./backend.fake.service";
import { BackendService } from "./backend.service";

@Injectable({
  providedIn: 'root'
})
export class RutinaService {

  constructor(private backend: BackendService) {}

  getRutinas(): Observable<Rutina[]> {
    return this.backend.getRutinas();
  }

  editarRutina(rutina: Rutina): Observable<Rutina> {
    return this.backend.putRutina(rutina);
  }

  eliminarRutina(nombre: string): Observable<void> {
    return this.backend.deleteRutina(nombre);
  }

  añadirRutina(rutina: Rutina): Observable<Rutina> {
    return this.backend.postRutina(rutina);
  }
}
