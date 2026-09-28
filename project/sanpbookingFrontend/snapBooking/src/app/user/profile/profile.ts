import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService, UserProfile } from '../../services/auth/auth-service';
import { BookingService } from '../../services/booking/booking-service';


@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class ProfileComponent implements OnInit {
  private authService = inject(AuthService);
  private bookingService = inject(BookingService);
  private fb = inject(FormBuilder);

  currentUser: UserProfile | null = null;
  profileForm: FormGroup;
  bookings: any[] = [];
  isLoadingBookings = true;
  activeTab: 'profile' | 'bookings' = 'profile';

  constructor() {
    this.profileForm = this.fb.group({
      username: [{ value: '', disabled: true }, Validators.required],
      name: ['', Validators.required],
      email: [{ value: '', disabled: true }, [Validators.required, Validators.email]]
    });
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();

    if (this.currentUser) {
      this.profileForm.patchValue({
        username: this.currentUser.username,
        name: this.currentUser.name,
        email: this.currentUser.email
      });
      this.loadUserBookings();
    }
  }

  loadUserBookings(): void {
    if (!this.currentUser) return;

    this.isLoadingBookings = true;
    this.bookingService.getBookingsByUserId(this.currentUser.id).subscribe({
      next: (data) => {
        this.bookings = data;
        this.isLoadingBookings = false;
      },
      error: (err) => {
        this.isLoadingBookings = false;
      }
    });
  }

  switchTab(tabName: 'profile' | 'bookings'): void {
    this.activeTab = tabName;
  }

  handleProfileUpdate(): void {
    if (this.profileForm.invalid || !this.currentUser) {
      return;
    }

    const updatedData = { name: this.profileForm.get('name')?.value };

    this.authService.updateUserProfile(this.currentUser.username, updatedData).subscribe({
      next: (updatedUser) => {
        this.currentUser = updatedUser;
        alert('Profile updated successfully!');
        this.profileForm.markAsPristine();
      },
      error: (err) => {
        alert('An error occurred while updating your profile.');
      }
    });
  }

  logout(): void {
    this.authService.logout();
  }
}
