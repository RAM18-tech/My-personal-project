import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth-service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private apiUrl = 'http://localhost:8000/package-api';
  private userUrl = 'http://localhost:8000/user-api/users';
  http = inject(HttpClient);
  authService = inject(AuthService);

  getAgents(): Observable<any[]> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<any[]>(`${this.userUrl}?role=agent`, options);
  }

  getUsers(): Observable<any[]> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<any[]>(`${this.userUrl}?role=user`, options);
  }

  getAdmins(): Observable<any[]> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<any[]>(`${this.userUrl}?role=admin`, options);
  }

  updateUserName(username: string, newName: string): Observable<any> {
    const body = { name: newName };
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.patch(`${this.userUrl}/${username}/name`, body, options);
  }

  deleteUser(username: string): Observable<any> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.delete(`${this.userUrl}/${username}`, options);
  }

  getPackagesByAgent(agentId: string): Observable<any[]> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.get<any[]>(`${this.apiUrl}/packages/agent/${agentId}`, options);
  }

  updatePackage(packageId: string, packageData: any): Observable<any> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.put(`${this.apiUrl}/packages/${packageId}`, packageData, options);
  }

  createPackage(packageData: any): Observable<any> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.post(`${this.apiUrl}/packages`, packageData, options);
  }

  deletePackage(packageId: string): Observable<any> {
    const options = { headers: this.authService.getAuthHeaders() };
    return this.http.delete(`${this.apiUrl}/packages/${packageId}`, options);
  }


  getAllPackages(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/packages`);
  }

  getPackageById(packageId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/packages/${packageId}`);
  }

  
}
