import { RouterModule, Routes } from '@angular/router';
import { ListadoEjercicioComponent } from './listado-ejercicios/listado-ejercicios.component';
import { ListadoRutinaComponent } from './listado-rutinas/listado-rutinas.component'; 
import { NgModule } from '@angular/core';
import { PruebaComponent } from './prueba/prueba.component';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';

export const routes: Routes = [
{ path: 'ejercicios', component: ListadoEjercicioComponent },
{ path: 'rutinas', component: ListadoRutinaComponent },
{ path: '', component: MenuPrincipalComponent },
{ path: 'menu', component: MenuPrincipalComponent },
{path: 'prueba', component:PruebaComponent}];

NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }