import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth-service';
 
export interface InsuranceData {
  insuranceId?: string;
  userId: string;
  bookingId?: string;
  provider: string;
  medicalEmergency: number;
  accidentalDeath: number;
  tripCancellation: number;
  startDate: string;
  endDate: string;
  status?: string;
  premium?: number;
  createdAt?: string;
}
 
@Injectable({
  providedIn: 'root'
})
export class InsuranceService {
 
  private apiUrl = 'http://localhost:8000/insurance-api';
  private http = inject(HttpClient);
  private authService = inject(AuthService);
 
  createInsurance(data: InsuranceData): Observable<InsuranceData> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.post<InsuranceData>(`${this.apiUrl}`, data, options);
  }
 
  getInsuranceByBooking(bookingId: string): Observable<InsuranceData[]> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<InsuranceData[]>(`${this.apiUrl}/booking/${bookingId}`, options);
  }
 
  getInsuranceByUser(userId: string): Observable<InsuranceData[]> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<InsuranceData[]>(`${this.apiUrl}/user/${userId}`, options);
  }

  updateInsurance(id: string, data: InsuranceData): Observable<InsuranceData> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.put<InsuranceData>(`${this.apiUrl}/${id}`, data, options);
  }

  deleteInsurance(id: string): Observable<void> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.delete<void>(`${this.apiUrl}/${id}`, options);
  }
}
