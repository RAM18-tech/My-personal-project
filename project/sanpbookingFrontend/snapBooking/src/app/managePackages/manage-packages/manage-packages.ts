import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { AdminService } from '../../services/admin/admin-service';
import { AuthService } from '../../services/auth/auth-service';
import { Review, ReviewService } from '../../services/review/review-service';
import { TravelPackage } from '../../services/packages/package-service';

export interface PackageWithReviews extends TravelPackage {
  reviews: Review[];
}

@Component({
  selector: 'app-manage-packages',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './manage-packages.html',
  styleUrls: ['./manage-packages.scss']
})
export class ManagePackages implements OnInit {
  packagesWithReviews: PackageWithReviews[] = [];
  isLoading = true;
  selectedPackage: PackageWithReviews | null = null;
  showReviewModal = false;
  replyForm: FormGroup;

  private adminService = inject(AdminService);
  private authService = inject(AuthService);
  private reviewService = inject(ReviewService);
  private fb = inject(FormBuilder);

  constructor() {
    this.replyForm = this.fb.group({});
  }

  ngOnInit(): void {
    const loggedInAgent = this.authService.getCurrentUser();
    if (loggedInAgent) {
      this.loadPackagesAndReviews(loggedInAgent.id);
    } else {
      this.isLoading = false;
    }
  }

  loadPackagesAndReviews(agentId: string): void {
    this.isLoading = true;
    this.adminService.getPackagesByAgent(agentId).subscribe({
      next: (packages) => {
        if (packages.length === 0) {
          this.packagesWithReviews = [];
          this.isLoading = false;
          return;
        }

        const reviewObservables = packages.map(pkg =>
          this.reviewService.getReviewsByPackageId(pkg.id.toString()).pipe(
            map(reviews => ({ ...pkg, reviews: reviews.sort((a, b) => new Date(b.reviewDate).getTime() - new Date(a.reviewDate).getTime()) }))
          )
        );

        forkJoin(reviewObservables).subscribe({
          next: (results) => {
            this.packagesWithReviews = results;
            this.isLoading = false;
          },
          error: (err) => {
            this.isLoading = false;
          }
        });
      },
      error: (err) => {
        this.isLoading = false;
      }
    });
  }

  deletePackage(packageId: string): void {
    if (confirm('Are you sure you want to permanently delete this package and all its data?')) {
      this.adminService.deletePackage(packageId).subscribe({
        next: () => {
          this.packagesWithReviews = this.packagesWithReviews.filter(p => p.id && p.id.toString() !== packageId);
        },
        error: (err) => {
        }
      });
    }
  }

  openReviewModal(pkg: PackageWithReviews): void {
    this.selectedPackage = pkg;
    this.replyForm = this.fb.group({});
    this.selectedPackage.reviews.forEach(review => {
      if (!review.agentResponse) {
        this.replyForm.addControl(`reply_${review.id}`, this.fb.control('', [Validators.required, Validators.minLength(10)]));
      }
    });
    this.showReviewModal = true;
  }

  closeReviewModal(): void {
    this.showReviewModal = false;
    this.selectedPackage = null;
  }

  handleReplySubmit(reviewId: string): void {
    const formControlName = `reply_${reviewId}`;
    const replyControl = this.replyForm.get(formControlName);

    if (!replyControl || replyControl.invalid) {
      replyControl?.markAsTouched();
      return;
    }

    const replyPayload = {
      agentResponse: replyControl.value
    };

    replyControl.disable();

    this.reviewService.addAgentResponse(reviewId, replyPayload).subscribe({
      next: (updatedReview) => {
        if (this.selectedPackage) {
          const reviewIndex = this.selectedPackage.reviews.findIndex(r => r.id === reviewId);
          if (reviewIndex > -1) {
            this.selectedPackage.reviews[reviewIndex] = updatedReview;
          }
          this.replyForm.removeControl(formControlName);
        }
      },
      error: (err) => {
        replyControl.enable();
      }
    });
  }

  getUnansweredReviewCount(pkg: PackageWithReviews): number {
    return pkg.reviews.filter(r => !r.agentResponse).length;
  }
}

