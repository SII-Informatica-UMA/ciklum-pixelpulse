import {MatGridListModule} from '@angular/material/grid-list';
import { EjerpruebaService } from '../servicios/ejerprueba.service';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}
@Component({
  selector: 'app-prueba',
  standalone: true,
 
  imports: [MatGridListModule,CommonModule,RouterOutlet,RouterLink,RouterLinkActive],
  providers:[EjerpruebaService],
  templateUrl: './prueba.component.html',
  styleUrl: './prueba.component.css'
})

export class PruebaComponent {
  constructor(public ejerprueba: EjerpruebaService) { }

  tiles: Tile[] = [
    {text: 'One', cols: 1, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightblue'},
   
  ];

}