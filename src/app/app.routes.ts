import { RouterModule, Routes } from '@angular/router';
import { EjerciciosComponent } from './ejercicios/ejercicios.component';
import { RutinasComponent } from './rutinas/rutinas.component'; 
import { NgModule } from '@angular/core';

export const routes: Routes = [
{ path: 'ejercicios', component: EjerciciosComponent },
{ path: 'rutinas', component: RutinasComponent },];

NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }