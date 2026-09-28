import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth-service';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8000/booking-api';
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  createBooking(bookingData: any): Observable<any> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.post(`${this.apiUrl}/bookings`, bookingData, options);
  }

  getBookingsByUserId(userId: string): Observable<any[]> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<any[]>(`${this.apiUrl}/bookings/user/${userId}`, options);
  }

  getBookingById(bookingId: string): Observable<any> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<any>(`${this.apiUrl}/bookings/${bookingId}`, options);
  }

  updateBookingStatus(id: string, status: string): Observable<any> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.patch(`${this.apiUrl}/bookings/${id}`, { status: status }, options);
  }
}