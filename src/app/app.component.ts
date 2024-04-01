import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { EjerciciosComponent } from './ejercicios/ejercicios.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,MenuPrincipalComponent,EjerciciosComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'proyectoSI';
}
