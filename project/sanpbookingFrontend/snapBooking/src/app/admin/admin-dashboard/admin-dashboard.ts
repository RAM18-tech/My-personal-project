import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AdminService } from '../../services/admin/admin-service.js';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

interface User {
  id: string;
  username: string;
  name: string;
  email: string;
  role: string[];
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {
  adminService = inject(AdminService);
  fb = inject(FormBuilder);

  agents: User[] = [];
  users: User[] = [];
  packages: any[] = [];
  
  filteredAgents: User[] = [];
  filteredUsers: User[] = [];

  selectedAgent: User | null = null;
  currentTab: 'agents' | 'users' = 'agents';
  agentSearchTerm = '';
  userSearchTerm = '';

  showUserModal = false;
  showPackageModal = false;
  isEditMode = false;
  
  userForm: FormGroup;
  packageForm: FormGroup;
  currentUser: User | null = null;
  currentEditingPackage: any = null;

  constructor() {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: [''],
      role: ['user', Validators.required],
    });

    this.packageForm = this.fb.group({
      name: ['', Validators.required],
      destination: ['', Validators.required],
      duration: ['', Validators.required],
      price: ['', [Validators.required, Validators.pattern(/^[0-9]+(\.[0-9]{1,2})?$/)]],
      description: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadAllData();
  }

  loadAllData(): void {
    this.adminService.getAgents().subscribe(data => {
      this.agents = data;
      this.filterAgents(); // Initial filter
    });
    this.adminService.getUsers().subscribe(data => {
      this.users = data;
      this.filterUsers(); // Initial filter
    });
  }

  filterAgents(): void {
    const term = this.agentSearchTerm.toLowerCase();
    this.filteredAgents = this.agents.filter(
      agent =>
        agent.name.toLowerCase().includes(term) ||
        agent.email.toLowerCase().includes(term)
    );
  }

  filterUsers(): void {
    const term = this.userSearchTerm.toLowerCase();
    this.filteredUsers = this.users.filter(
      user =>
        user.name.toLowerCase().includes(term) ||
        user.email.toLowerCase().includes(term)
    );
  }

  switchTab(tab: 'agents' | 'users'): void {
    this.currentTab = tab;
    this.selectedAgent = null;
    this.packages = [];
  }

  viewAgentPackages(agent: User): void {
    this.selectedAgent = agent;
    this.adminService.getPackagesByAgent(agent.id).subscribe(data => {
      this.packages = data;
    });
  }
  
  private getPrimaryRole(roles: any): string {
    if (!Array.isArray(roles) || roles.length === 0) return 'user';
    if (roles.includes('ROLE_AGENT')) return 'agent';
    return 'user';
  }

  openAddUserModal(role: 'agent' | 'user'): void {
    this.isEditMode = false;
    this.currentUser = null;
    this.userForm.reset({ role: role, name: '', email: '', password: '' });
    this.userForm.get('email')?.enable();
    this.userForm.get('role')?.enable();
    this.userForm.get('password')?.setValidators([Validators.required, Validators.minLength(6)]);
    this.userForm.get('password')?.updateValueAndValidity();
    this.showUserModal = true;
  }

  openEditUserModal(user: User): void {
    this.isEditMode = true;
    this.currentUser = { ...user };
    const primaryRole = this.getPrimaryRole(user.role);
    this.userForm.setValue({
      name: user.name,
      email: user.email,
      password: '',
      role: primaryRole,
    });
    this.userForm.get('email')?.disable();
    this.userForm.get('role')?.disable();
    this.userForm.get('password')?.clearValidators();
    this.userForm.get('password')?.updateValueAndValidity();
    this.showUserModal = true;
  }

  closeUserModal(): void {
    this.showUserModal = false;
  }

  handleUserFormSubmit(): void {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }

    let apiCall: Observable<any>;
    let successMessage: string;

    if (this.isEditMode && this.currentUser) {
      const { username } = this.currentUser;
      const newName = this.userForm.get('name')?.value;
      apiCall = this.adminService.updateUserName(username, newName);
      successMessage = 'User name updated successfully!';
    } else {
      alert('User creation is handled through registration page.');
      this.closeUserModal();
      return;
    }
    
    apiCall.pipe(
      tap(() => {
        alert(successMessage);
        this.loadAllData();
        this.closeUserModal();
      }),
      catchError(err => {
        alert('An error occurred. Please try again.');
        return of(null);
      })
    ).subscribe();
  }

  openEditPackageModal(pkg: any): void {
    this.currentEditingPackage = { ...pkg };
    this.packageForm.patchValue(pkg);
    this.showPackageModal = true;
  }

  closePackageModal(): void {
    this.showPackageModal = false;
  }

  handlePackageFormSubmit(): void {
    if (this.packageForm.invalid || !this.currentEditingPackage) {
      this.packageForm.markAllAsTouched();
      return;
    }
    const updatedPackageData = { ...this.currentEditingPackage, ...this.packageForm.value };
    this.adminService.updatePackage(this.currentEditingPackage.id, updatedPackageData).subscribe(() => {
      alert('Package updated successfully!');
      this.closePackageModal();
      if (this.selectedAgent) this.viewAgentPackages(this.selectedAgent);
    });
  }

  deleteUser(username: string): void {
    if (confirm('Are you sure you want to permanently delete this user? This action cannot be undone.')) {
      this.adminService.deleteUser(username).subscribe(() => {
        alert('User deleted successfully!');
        this.loadAllData();
        if (this.selectedAgent?.username === username) {
          this.selectedAgent = null;
          this.packages = [];
        }
      });
    }
  }

  deletePackage(packageId: string): void {
    if (confirm('Are you sure you want to delete this package?')) {
      this.adminService.deletePackage(packageId).subscribe(() => {
        alert('Package deleted successfully!');
        if (this.selectedAgent) this.viewAgentPackages(this.selectedAgent);
      });
    }
  }
}