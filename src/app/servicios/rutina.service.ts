import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rutina } from '../rutinas/rutina';

@Injectable({
  providedIn: 'root'
})
export class RutinaService {
  private apiUrl = 'http://tu-api.com/rutinas'; 
  constructor(private http: HttpClient) { }

  getRutinas(): Observable<Rutina[]> {
    return this.http.get<Rutina[]>(this.apiUrl);
  }
}
