import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HomePage } from "./home/home-page/home-page";
import { Footer } from "./layout/footer/footer";
import { Navbar } from "./layout/navbar/navbar";

@Component({
  selector: 'app-root',
  imports: [ Footer, Navbar, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('snapBooking');
}
