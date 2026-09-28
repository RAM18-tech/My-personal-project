import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-popular-packages',
  imports: [CommonModule,RouterLink],
  templateUrl: './popular-packages.html',
  styleUrl: './popular-packages.css'
})
export class PopularPackages {

  @ViewChild('scrollContainer') scrollContainer!: ElementRef;

  packages = [
    {
      from: 'Visakhapatnam',
      to: 'Dubai',
      nights: 4,
      description: 'Luxury & desert views',
      rating: 4.5,
      reviews: 128,
      thumbImage: 'assets/images/vskp.jpg',
      cardBgImage: 'assets/images/dubai.jpg'
    },
    {
      from: 'Hyderabad',
      to: 'Singapore',
      nights: 5,
      description: 'Modern city getaway',
      rating: 4.8,
      reviews: 212,
      thumbImage: 'assets/images/hyd.jpg',
      cardBgImage: 'assets/images/singapore.jpg'
    },
    {
      from: 'Mumbai',
      to: 'Bali',
      nights: 4,
      description: 'Tropical paradise',
      rating: 4.7,
      reviews: 150,
      thumbImage: 'assets/images/mum.jpg',
      cardBgImage: 'assets/images/bali.jpg'
    },
    {
      from: 'Chennai',
      to: 'London',
      nights: 7,
      description: 'Historic city adventure',
      rating: 4.9,
      reviews: 180,
      thumbImage: 'assets/images/che.jpg',
      cardBgImage: 'assets/images/londan.jpg'
    },
    {
      from: 'Delhi',
      to: 'Paris',
      nights: 6,
      description: 'Romantic city escape',
      rating: 4.7,
      reviews: 145,
      thumbImage: 'assets/images/del.jpg',
      cardBgImage: 'assets/images/paris.jpg'
    },
  ];

  scrollLeft() {
    this.scrollContainer.nativeElement.scrollBy({ left: -300, behavior: 'smooth' });
  }

  scrollRight() {
    this.scrollContainer.nativeElement.scrollBy({ left: 300, behavior: 'smooth' });
  }



}
