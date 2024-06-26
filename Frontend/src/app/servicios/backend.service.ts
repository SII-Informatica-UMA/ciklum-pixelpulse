import { Injectable } from "@angular/core";
import { Observable, map, of } from "rxjs";
import { Rutina } from "../entities/rutina";
import { Ejercicio } from "../entities/ejercicio";
import { HttpClient } from "@angular/common/http";
import { BACKEND_URI } from "../config/config";
import { UsuariosService } from "../services/usuarios.service";
import { switchMap } from 'rxjs/operators';

// Este servicio usa el backend real

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private httpClient: HttpClient,private usuarioService: UsuariosService) {}

  getRutinas(entrenadorId: string): Observable<Rutina[]> {
    return this.httpClient.get<Rutina[]>(`${BACKEND_URI}/rutina?entrenador=${entrenadorId}`);
  }

 postRutina(rutina: Rutina, entrenadorId: string): Observable<Rutina> {
  return this.httpClient.post<Rutina>(`${BACKEND_URI}/rutina?entrenador=${entrenadorId}`, rutina);
}

getRutina(id: number, entrenadorId: string): Observable<Rutina> {
  return this.httpClient.get<Rutina>(`${BACKEND_URI}/rutina/${id}?entrenador=${entrenadorId}`);
}

putRutina(id: number, rutina: Rutina, entrenadorId: string): Observable<Rutina> {
  return this.httpClient.put<Rutina>(`${BACKEND_URI}/rutina/${id}?entrenador=${entrenadorId}`, rutina);
}

deleteRutina(id: number, entrenadorId: string): Observable<void> {
  return this.httpClient.delete<void>(`${BACKEND_URI}/rutina/${id}?entrenador=${entrenadorId}`);
}

 
  getEjercicios(entrenadorId: string): Observable<Ejercicio[]> {
      return this.httpClient.get<Ejercicio[]>(`${BACKEND_URI}/ejercicio?entrenador=${entrenadorId}`);
    }


 postEjercicio(Ejercicio: Ejercicio,entrenadorId: string): Observable<Ejercicio> {
      return this.httpClient.post<Ejercicio>(`${BACKEND_URI}/ejercicio?entrenador=${entrenadorId}`, Ejercicio);
}


  putEjercicio(idEjercicio: number, Ejercicio: Ejercicio,entrenadorId: string): Observable<Ejercicio> {
      return this.httpClient.put<Ejercicio>(`${BACKEND_URI}/ejercicio/${idEjercicio}?entrenador=${entrenadorId}`, Ejercicio);
}

deleteEjercicio(idEjercicio: number,entrenadorId: string): Observable<void> {
      return this.httpClient.delete<void>(`${BACKEND_URI}/ejercicio/${idEjercicio}?entrenador=${entrenadorId}`);
 
}

  getEjercicio(idEjercicio: number,entrenadorId: string): Observable<Ejercicio> {
      return this.httpClient.get<Ejercicio>(`${BACKEND_URI}/ejercicio/${idEjercicio}?entrenador=${entrenadorId}`);
}
  
  
}