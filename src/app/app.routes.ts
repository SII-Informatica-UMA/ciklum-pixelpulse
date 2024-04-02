import { RouterModule, Routes } from '@angular/router';
import { EjerciciosComponent } from './ejercicios/ejercicios.component';
import { RutinasComponent } from './rutinas/rutinas.component'; 
import { NgModule } from '@angular/core';
import { PruebaComponent } from './prueba/prueba.component';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';

export const routes: Routes = [
{ path: 'ejercicios', component: EjerciciosComponent },
{ path: 'rutinas', component: RutinasComponent },
{ path: '', component: MenuPrincipalComponent },
{path: 'prueba', component:PruebaComponent}];

NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }