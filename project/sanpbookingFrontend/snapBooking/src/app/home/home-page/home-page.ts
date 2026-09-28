import { Component } from '@angular/core';
import { Navbar } from "../../layout/navbar/navbar";
import { Carousel } from "../carousel/carousel";
import { Desc } from "../desc/desc";
import { PopularPackages } from "../popular-packages/popular-packages";
import { Testimonials } from "../testimonials/testimonials";
import { OurPartners } from "../our-partners/our-partners";
import { Footer } from "../../layout/footer/footer";

@Component({
  selector: 'app-home-page',
  imports: [Carousel, Desc, PopularPackages, Testimonials, OurPartners],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css'
})
export class HomePage {

}
