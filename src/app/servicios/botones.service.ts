import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BotonesService {

 showMenu = true;
 showEjercicios = false;
 showRutinas = false;
 showPrueba = false;

  cambiarMenu(){
    this.showMenu = true;
    this.showEjercicios = false;
    this.showRutinas = false;
    this.showPrueba = false;
  }

  cambiarEjercicios(){
    this.showMenu = false;
    this.showEjercicios = true;
    this.showRutinas = false;
    this.showPrueba = false;
  }
  cambiarRutinas(){
    this.showMenu = false;
    this.showEjercicios = false;
    this.showRutinas = true;
    this.showPrueba = false;
  }
  cambiarPrueba(){
    this.showMenu = false;
    this.showEjercicios = false;
    this.showRutinas = false;
    this.showPrueba = true;
  }
}
