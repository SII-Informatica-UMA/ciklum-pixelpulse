import { Injectable } from "@angular/core";
import { Observable, map, of } from "rxjs";
import { Rutina } from "../entities/rutina";
import { Ejercicio } from "../entities/ejercicio";
import { HttpClient } from "@angular/common/http";
import { BACKEND_URI } from "../config/config";


// Este servicio usa el backend real

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private httpClient: HttpClient) {}

  getRutinas(): Observable<Rutina[]> {
    return this.httpClient.get<Rutina[]>(BACKEND_URI + '/Rutina');
  }

  postRutina(Rutina: Rutina): Observable<Rutina> {
    return this.httpClient.post<Rutina>(BACKEND_URI + '/Rutina', Rutina);
  }

  putRutina(Rutina: Rutina): Observable<Rutina> {
    return this.httpClient.put<Rutina>(BACKEND_URI + '/Rutina/' + Rutina.nombre, Rutina);
  }

  deleteRutina(nombre: String): Observable<void> {
    return this.httpClient.delete<void>(BACKEND_URI + '/Rutina/' + nombre);
  }

  getRutina(nombre: String): Observable<Rutina> {
    return this.httpClient.get<Rutina>(BACKEND_URI + '/Rutina/' + nombre);
  }

 
  getEjercicios(): Observable<Ejercicio[]> {
    return this.httpClient.get<Ejercicio[]>(BACKEND_URI + '/Ejercicio');
  }

  postEjercicio(Ejercicio: Ejercicio): Observable<Ejercicio> {
    return this.httpClient.post<Ejercicio>(BACKEND_URI + '/Ejercicio', Ejercicio);
  }

  putEjercicio(Ejercicio: Ejercicio): Observable<Ejercicio> {
    return this.httpClient.put<Ejercicio>(BACKEND_URI + '/Ejercicio/' + Ejercicio.id, Ejercicio);
  }

  deleteEjercicio(id: number): Observable<void> {
    return this.httpClient.delete<void>(BACKEND_URI + '/Ejercicio/' + id);
  }

  getEjercicio(id: number): Observable<Ejercicio> {
    return this.httpClient.get<Ejercicio>(BACKEND_URI + '/Ejercicio/' + id);
  }
  
  
}