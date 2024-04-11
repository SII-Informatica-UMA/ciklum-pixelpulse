import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ForgottenPasswordComponent } from './forgotten-password/forgotten-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ListadoUsuarioComponent } from './listado-usuario/listado-usuario.component';
import { PrincipalComponent } from './principal/principal.component';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { ListadoEjercicioComponent } from './listado-ejercicios/listado-ejercicios.component';
import { ListadoRutinaComponent } from './listado-rutinas/listado-rutinas.component';

export const routes: Routes = [
  {path : 'ejercicios',component: ListadoEjercicioComponent},
  {path : 'rutinas',component: ListadoRutinaComponent},
  {path : 'menu-principal',component: MenuPrincipalComponent},
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'forgotten-password',
    component: ForgottenPasswordComponent
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent
  },
  {
    path: 'usuarios',
    component: ListadoUsuarioComponent
  },
  {
    path: '',
    component: PrincipalComponent
  }
];
