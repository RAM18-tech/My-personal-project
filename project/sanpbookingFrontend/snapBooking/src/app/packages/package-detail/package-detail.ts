import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Subscription, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ReviewFormComponent } from '../../reviews/review-form/review-form';
import { AdminService } from '../../services/admin/admin-service';
import { AuthService } from '../../services/auth/auth-service';
import { BookingService } from '../../services/booking/booking-service';
import { InsuranceService } from '../../services/insurance/insurance';
import { Review, ReviewService } from '../../services/review/review-service';


interface TravelPackage {
  id: string;
  name: string;
  destination: string;
  duration: number;
  description: string;
  price: number;
  imageUrl: string;
}

interface Booking {
  id: string;
}

@Component({
  selector: 'app-package-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    CurrencyPipe,
    ReviewFormComponent
  ],
  templateUrl: './package-detail.html',
  styleUrls: ['./package-detail.scss']
})
export class PackageDetail implements OnInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private adminService = inject(AdminService);
  private authService = inject(AuthService);
  private bookingService = inject(BookingService);
  private insuranceService = inject(InsuranceService);
  private reviewService = inject(ReviewService);

  currentPackage: TravelPackage | null = null;
  reviews: Review[] = [];
  isLoading = true;
  private formChangesSubscription: Subscription | undefined;
  editingReviewId: string | null = null;
  editForm: FormGroup;

  showBookingModal = false;
  bookingForm: FormGroup;
  insuranceCostPerTraveler = 0;
  totalBookingCost: number = 0;
  
  notificationMessage: string | null = null;
  
  constructor() {
    this.bookingForm = this.fb.group({
      travelDate: ['', Validators.required],
      numberOfTravelers: [1, [Validators.required, Validators.min(1), Validators.max(10)]],
      insuranceSelected: [false]
    });
    this.editForm = this.fb.group({
      rating: [0, [Validators.required, Validators.min(1)]],
      comment: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  ngOnInit(): void {
    this.loadUserInsurancePremium();
    this.listenToFormChanges();

    this.route.paramMap.pipe(
      switchMap(params => {
        const id = params.get('id');
        if (id) {
          this.isLoading = true;
          return this.adminService.getPackageById(id);
        }
        return of(null);
      })
    ).subscribe(pkg => {
      this.currentPackage = pkg;
      if (pkg) {
        this.loadReviews(pkg.id);
      }
      this.isLoading = false;
    });

    const today = new Date().toISOString().split('T')[0];
    this.bookingForm.patchValue({ travelDate: today });
  }



  loadReviews(packageId: string): void {
    this.reviewService.getReviewsByPackageId(packageId).subscribe({
      next: (reviews) => {
        this.reviews = reviews;
      },
      error: (err) => {
        this.showNotification('Could not load reviews at this time.');
      }
    });
  }

  get canUserReview(): boolean {
    return this.authService.isLoggedIn() && this.authService.getPrimaryRole() === 'ROLE_USER';
  }
  
  get currentUser() {
    return this.authService.getCurrentUser();
  }
  
  private listenToFormChanges(): void {
    this.formChangesSubscription = this.bookingForm.valueChanges.subscribe(formValue => {
      if (!this.currentPackage || !formValue.numberOfTravelers) {
        this.totalBookingCost = 0;
        return;
      }
      const travelers = formValue.numberOfTravelers;
      const baseCost = this.currentPackage.price * travelers;
      const insuranceCost = formValue.insuranceSelected ? this.insuranceCostPerTraveler * travelers : 0;
      this.totalBookingCost = baseCost + insuranceCost;
    });
  }

  openBookingModal(): void {
    if (!this.authService.isLoggedIn()) {
      this.showNotification('Please log in to book a trip.');
      this.router.navigate(['/login']);
      return;
    }
    if (this.authService.getPrimaryRole() !== 'ROLE_USER') {
      this.showNotification('Only customers can book trips. Admins and Agents cannot book.');
      return;
    }
    this.bookingForm.updateValueAndValidity(); 
    this.showBookingModal = true;
  }

  closeBookingModal(): void {
    this.showBookingModal = false;
  }

  handleBookingSubmit(): void {
    if (this.bookingForm.invalid) {
      this.bookingForm.markAllAsTouched();
      this.showNotification('Please fill in all required fields correctly.');
      return;
    }
    const user = this.currentUser;
    if (!this.currentPackage || !user) {
      this.showNotification('Error: Package or user data not loaded.');
      return;
    }

    const formValue = this.bookingForm.value;
    const bookingPayload = {
      userId: user.id,
      userName: user.name,
      packageId: this.currentPackage.id,
      packageName: this.currentPackage.name,
      travelDate: formValue.travelDate,
      numberOfTravelers: formValue.numberOfTravelers,
      totalPrice: this.totalBookingCost,
      insuranceSelected: formValue.insuranceSelected,
      bookingDate: new Date().toISOString(),
      status: 'pending'
    };

    this.bookingService.createBooking(bookingPayload).subscribe({
      next: (newBooking: Booking) => {
        this.closeBookingModal();
        this.router.navigate(['/payment', newBooking.id]); 
      },
      error: (err) => {
        this.showNotification('Sorry, there was an error with your booking.');
      }
    });
  }

  onReviewSubmitted(): void {
    this.showNotification('Thank you for your review!');
    if (this.currentPackage) {
      this.loadReviews(this.currentPackage.id);
    }
  }

  canEditReview(review: Review): boolean {
    return this.currentUser?.id === review.userId;
  }

  startEditReview(review: Review): void {
    this.editingReviewId = review.id;
    this.editForm.patchValue({ rating: review.rating, comment: review.comment });
  }

  cancelEdit(): void {
    this.editingReviewId = null;
  }

  saveEditReview(reviewId: string): void {
    if (this.editForm.invalid) return;
    this.reviewService.updateReview(reviewId, this.editForm.value).subscribe({
      next: () => {
        this.editingReviewId = null;
        if (this.currentPackage) this.loadReviews(this.currentPackage.id);
        this.showNotification('Review updated successfully!');
      },
      error: () => this.showNotification('Failed to update review.')
    });
  }

  deleteReview(reviewId: string): void {
    if (!confirm('Delete this review?')) return;
    this.reviewService.deleteReview(reviewId).subscribe({
      next: () => {
        if (this.currentPackage) this.loadReviews(this.currentPackage.id);
        this.showNotification('Review deleted successfully!');
      },
      error: () => this.showNotification('Failed to delete review.')
    });
  }
  
  showNotification(message: string): void {
    this.notificationMessage = message;
    setTimeout(() => this.notificationMessage = null, 3000);
  }

  private loadUserInsurancePremium(): void {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.insuranceService.getInsuranceByUser(currentUser.id).subscribe({
        next: (insurances: any[]) => {
          if (insurances.length > 0) {
            this.insuranceCostPerTraveler = insurances[insurances.length - 1].premium || 50;
          } else {
            this.insuranceCostPerTraveler = 50;
          }
        },
        error: () => {
          this.insuranceCostPerTraveler = 50;
        }
      });
    } else {
      this.insuranceCostPerTraveler = 50;
    }
  }

  ngOnDestroy(): void {
    if (this.formChangesSubscription) {
      this.formChangesSubscription.unsubscribe();
    }
  }
}