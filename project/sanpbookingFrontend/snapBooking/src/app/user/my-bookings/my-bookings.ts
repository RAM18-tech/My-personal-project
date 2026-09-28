import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BookingService } from '../../services/booking/booking-service';
import { AuthService } from '../../services/auth/auth-service';

@Component({
  selector: 'app-my-bookings',
  imports: [CommonModule, RouterModule],
  templateUrl: './my-bookings.html',
  styleUrl: './my-bookings.css'
})
export class MyBookings {

  bookings: any[] = [];
  isLoading = true;
  currentUser: any = null;

  private bookingService = inject(BookingService);
  private authService = inject(AuthService);

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (this.currentUser) {
      this.loadUserBookings();
    } else {
      this.isLoading = false;
    }
  }

  loadUserBookings(): void {
    this.bookingService.getBookingsByUserId(this.currentUser.id).subscribe({
      next: (data) => {
        this.bookings = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
      }
    });
  }

}
