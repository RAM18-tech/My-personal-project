import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReviewService } from '../../services/review/review-service';
@Component({
  selector: 'app-review-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './review-form.html',
  styleUrls: ['./review-form.scss']
})
export class ReviewFormComponent implements OnInit {
  @Input() packageId!: string;
  @Input() userId!: string;
  @Input() userName!: string;

  @Output() reviewSubmitted = new EventEmitter<void>();

  private fb = inject(FormBuilder);
  private reviewService = inject(ReviewService);

  reviewForm!: FormGroup;
  hoverRating = 0;
  isSubmitting = false;

  ngOnInit(): void {
    this.reviewForm = this.fb.group({
      rating: [0, [Validators.required, Validators.min(1)]],
      comment: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  setRating(rating: number): void {
    this.reviewForm.get('rating')?.setValue(rating);
  }

  onSubmit(): void {
    if (this.reviewForm.invalid) {
      this.reviewForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;

    const reviewData = {
      ...this.reviewForm.value,
      packageId: this.packageId,
      userId: this.userId,
      userName: this.userName,
      reviewDate: new Date().toISOString().split('T')[0]
    };

    this.reviewService.createReview(reviewData).subscribe({
      next: () => {
        alert('Thank you for your review!');
        this.isSubmitting = false;
        this.reviewSubmitted.emit();
      },
      error: (err) => {
        alert('There was an error submitting your review. Please try again.');
        this.isSubmitting = false;
      }
    });
  }
}
