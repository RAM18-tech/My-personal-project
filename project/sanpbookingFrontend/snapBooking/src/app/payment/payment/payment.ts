import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { BookingService } from '../../services/booking/booking-service';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, RouterLink],
  templateUrl: './payment.html',
  styleUrls: ['./payment.scss']
})
export class PaymentComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private bookingService = inject(BookingService);

  booking$: Observable<any | null> | undefined;

  ngOnInit(): void {
    this.booking$ = this.route.paramMap.pipe(
      switchMap(params => {
        const bookingId = params.get('bookingId');
        return bookingId ? this.bookingService.getBookingById(bookingId) : of(null);
      })
    );
  }

  getInsuranceCost(booking: any): number {
    return booking.numberOfTravelers * 50;
  }

  processPayment(booking: any): void {
    this.bookingService.updateBookingStatus(booking.id, 'confirmed').subscribe({
      next: () => {
        alert('Payment Successful! Your booking is confirmed.');
        this.router.navigate(['/my-bookings']);
      },
      error: (err) => {
        alert('There was an issue confirming your booking. Please contact support.');
      }
    });
  }
}