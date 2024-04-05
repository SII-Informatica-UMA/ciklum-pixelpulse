import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { ListadoEjercicioComponent } from './listado-ejercicios/listado-ejercicios.component';
import { CommonModule } from '@angular/common';
import { BotonesService } from './servicios/botones.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,MenuPrincipalComponent,CommonModule,RouterLink],
  providers: [BotonesService],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  constructor(private serviceBotones: BotonesService) { }

   menuPrin: boolean =  true;
   showEjercios: boolean =  true;
   showRutinas: boolean =  true;
}

