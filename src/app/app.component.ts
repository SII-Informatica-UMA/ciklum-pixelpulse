import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { EjerciciosComponent } from './ejercicios/ejercicios.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,MenuPrincipalComponent,EjerciciosComponent,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
   menuPrin: boolean =  true;
   showEjercios: boolean =  true;
   showRutinas: boolean =  true;
}

