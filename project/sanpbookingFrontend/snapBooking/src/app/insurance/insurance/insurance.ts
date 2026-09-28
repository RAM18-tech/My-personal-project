import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { InsuranceService, InsuranceData } from '../../services/insurance/insurance';
import { AuthService } from '../../services/auth/auth-service';
 
@Component({
  selector: 'app-insurance',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './insurance.html',
  styleUrl: './insurance.css'
})
export class InsuranceComponent implements OnInit {
 
  @Input() bookingId: string = '';
  @Input() userId: string = '';
  @Input() showModal = false;
  @Output() insuranceCreated = new EventEmitter<InsuranceData>();
  @Output() modalClosed = new EventEmitter<void>();
 
  private fb = inject(FormBuilder);
  private insuranceService = inject(InsuranceService);
  private router = inject(Router);
  private authService = inject(AuthService);
 
  selectedPackage: any = null;
  isStandalonePage = false;
  userInsurances: InsuranceData[] = [];
  isLoading = true;
  showAddForm = false;
  editingInsurance: InsuranceData | null = null;
 
  insuranceForm = this.fb.group({
    provider: ['', Validators.required],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required]
  });
 
  coveragePackages = [
    {
      name: 'Basic Coverage',
      medicalEmergency: 50000,
      accidentalDeath: 200000,
      tripCancellation: 10000,
      premium: 50
    },
    {
      name: 'Premium Coverage',
      medicalEmergency: 100000,
      accidentalDeath: 500000,
      tripCancellation: 25000,
      premium: 100
    }
  ];
 
  providers = ['LIC', 'HDFC ERGO', 'ICICI Lombard', 'Bajaj Allianz'];
 
  ngOnInit() {
    this.isStandalonePage = !this.bookingId && !this.userId && !this.showModal;
   
    if (this.isStandalonePage && !this.authService.isLoggedIn()) {
      alert('Please login to access insurance services.');
      this.router.navigate(['/login']);
      return;
    }
   
    if (this.isStandalonePage) {
      this.loadUserInsurance();
    }
  }
 
  selectPackage(pkg: any) {
    this.selectedPackage = pkg;
  }
 
  handleSubmit() {
    if (!this.selectedPackage || this.insuranceForm.invalid) {
      this.insuranceForm.markAllAsTouched();
      return;
    }
   
    const currentUser = this.authService.getCurrentUser();
    const insuranceData: InsuranceData = {
      userId: this.isStandalonePage ? currentUser?.id || '' : this.userId,
      bookingId: this.bookingId || undefined,
      provider: this.insuranceForm.value.provider!,
      medicalEmergency: this.selectedPackage.medicalEmergency,
      accidentalDeath: this.selectedPackage.accidentalDeath,
      tripCancellation: this.selectedPackage.tripCancellation,
      startDate: this.insuranceForm.value.startDate!,
      endDate: this.insuranceForm.value.endDate!
    };
   
    if (this.editingInsurance && this.editingInsurance.insuranceId) {
      insuranceData.insuranceId = this.editingInsurance.insuranceId;
      this.insuranceService.updateInsurance(this.editingInsurance.insuranceId!, insuranceData).subscribe({
        next: (insurance) => {
          this.insuranceCreated.emit(insurance);
          if (this.isStandalonePage) {
            alert('Insurance updated successfully!');
            this.showAddForm = false;
            this.resetForm();
            this.loadUserInsurance();
          } else {
            this.closeModal();
          }
        },
        error: (err) => {
        }
      });
    } else {
      this.insuranceService.createInsurance(insuranceData).subscribe({
        next: (insurance) => {
          this.insuranceCreated.emit(insurance);
          if (this.isStandalonePage) {
            alert('Insurance created successfully!');
            this.showAddForm = false;
            this.loadUserInsurance();
            this.resetForm();
          } else {
            this.closeModal();
          }
        },
        error: (err) => {
        }
      });
    }
  }
 
  closeModal() {
    this.showModal = false;
    this.modalClosed.emit();
    this.resetForm();
  }
 
  skipInsurance() {
    this.closeModal();
  }
 
  goToPackages() {
    this.router.navigate(['/packages']);
  }
 
  private resetForm() {
    this.insuranceForm.reset();
    this.selectedPackage = null;
    this.editingInsurance = null;
  }
 
  loadUserInsurance() {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.insuranceService.getInsuranceByUser(currentUser.id).subscribe({
        next: (insurances) => {
          this.userInsurances = insurances;
          this.isLoading = false;
        },
        error: (err) => {
          this.isLoading = false;
        }
      });
    }
  }
 
  showAddInsuranceForm() {
    this.showAddForm = true;
    this.editingInsurance = null;
  }
 
  editInsurance(insurance: InsuranceData) {
    this.editingInsurance = { ...insurance };
    this.showAddForm = true;
    this.insuranceForm.patchValue({
      provider: insurance.provider,
      startDate: insurance.startDate,
      endDate: insurance.endDate
    });
    this.selectedPackage = this.coveragePackages.find(pkg => pkg.premium === insurance.premium);
  }
 
  deleteInsurance(insuranceId: string) {
    if (confirm('Are you sure you want to delete this insurance?')) {
      this.insuranceService.deleteInsurance(insuranceId).subscribe({
        next: () => {
          alert('Insurance deleted successfully!');
          this.loadUserInsurance();
        },
        error: (err) => {
          alert('Failed to delete insurance. Please try again.');
        }
      });
    }
  }
 
  cancelAddEdit() {
    this.showAddForm = false;
    this.editingInsurance = null;
    this.resetForm();
  }
}
 
