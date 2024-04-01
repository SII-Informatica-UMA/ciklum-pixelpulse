import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ejercicio } from '../ejercicios/ejercicio';

@Injectable({
  providedIn: 'root'
})
export class EjercicioService {
  private apiUrl = 'http://tu-api.com/ejercicios'; 

  constructor(private http: HttpClient) { }

  getEjercicios(): Observable<Ejercicio[]> {
    return this.http.get<Ejercicio[]>(this.apiUrl);
  }
}
