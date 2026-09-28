import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AdminService } from '../../services/admin/admin-service';
import { AuthService } from '../../services/auth/auth-service';
import { of, switchMap } from 'rxjs';

@Component({
  selector: 'app-add-packages',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './add-packages.html',
  styleUrl: './add-packages.css'
})
export class AddPackages implements OnInit {

  packageForm: FormGroup;
  isEditMode = false;
  private packageId: string | null = null;
  private loggedInAgent: any = null;

  private currentEditingPackage: any = null;

  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private adminService = inject(AdminService);
  private authService = inject(AuthService);

  constructor() {
    this.packageForm = this.fb.group({
      name: ['', Validators.required],
      destination: ['', Validators.required],
      duration: ['', Validators.required],
      price: ['', [Validators.required, Validators.pattern(/^[0-9]+(\.[0-9]{1,2})?$/)]],
      description: ['', Validators.required],
      imageUrl: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loggedInAgent = this.authService.getCurrentUser();

    this.route.paramMap.pipe(
      switchMap(params => {
        this.packageId = params.get('id');
        if (this.packageId) {
          this.isEditMode = true;
          return this.adminService.getPackagesByAgent(this.loggedInAgent.id);

        }
        return of(null);
      })
    ).subscribe(packages => {
      if (this.isEditMode && packages && this.packageId) {
        const packageData = packages.find(p => p.id == this.packageId);
        if (packageData) {
          this.currentEditingPackage = { ...packageData };
          this.packageForm.patchValue(packageData);
        } else {
          alert("Error: Package not found or you don't have permission to edit it.");
          this.router.navigate(['/agent/dashboard']);
        }
      }
    });
  }

  handleFormSubmit(): void {
    if (this.packageForm.invalid) {
      this.packageForm.markAllAsTouched();
      return;
    }

    if (this.isEditMode && this.packageId) {
      const updatedData = {
        ...this.currentEditingPackage,
        ...this.packageForm.value
      };

      this.adminService.updatePackage(this.packageId, updatedData).subscribe(() => {
        alert('Package updated successfully!');
        this.router.navigate(['/agent/dashboard']);
      });
    } else {
      const newData = {
        ...this.packageForm.value,
        agentId: this.loggedInAgent.id
      };

      this.adminService.createPackage(newData).subscribe({
        next: () => {
          alert('New package created successfully!');
          this.router.navigate(['/agent/dashboard']);
        },
        error: (err) => {
          alert('An error occurred while creating the package.');
        }
      });
    }
  }
}
