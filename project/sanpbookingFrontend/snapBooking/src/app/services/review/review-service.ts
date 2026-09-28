import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth-service';

export interface Review {
  id: string;
  packageId: string;
  userId: string;
  userName: string;
  rating: number;
  comment: string;
  reviewDate: string;
  agentResponse?: string;
  responseDate?: string;
}

export interface AgentResponse {
  agentResponse: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private apiUrl = 'http://localhost:8000/review-api/reviews';
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  createReview(reviewData: Partial<Review>): Observable<Review> {
    let options = { headers: this.authService.getAuthHeaders() };
    return this.http.post<Review>(this.apiUrl, reviewData, options);
  }


  getReviewsByPackageId(packageId: string): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/package/${packageId}`);
  }

  addAgentResponse(reviewId: string, response: AgentResponse): Observable<Review> {
    let options = { headers: this.authService.getAuthHeaders() };
    return this.http.patch<Review>(`${this.apiUrl}/${reviewId}/reply`, response, options);
  }

  updateReview(reviewId: string, reviewData: Partial<Review>): Observable<Review> {
    let options = { headers: this.authService.getAuthHeaders() };
    return this.http.put<Review>(`${this.apiUrl}/${reviewId}`, reviewData, options);
  }

  deleteReview(reviewId: string): Observable<void> {
    let options = { headers: this.authService.getAuthHeaders() };
    return this.http.delete<void>(`${this.apiUrl}/${reviewId}`, options);
  }
}
