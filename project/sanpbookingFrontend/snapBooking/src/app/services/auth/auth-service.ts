import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap, switchMap, of } from 'rxjs';


export interface LoginPayload {
  usernameOrEmail: string;
  password?: string;
}

export interface RegisterPayload {
  name?: string | null;
  username?: string | null;
  email?: string | null;
  password?: string | null;
  registerAsAgent?: boolean | null;
}

export interface AuthResponse {
  accessToken: string;
}

export interface UserProfile {
  id: string;
  name: string;
  username: string;
  email: string;
  roles: string[];
}

export interface UpdateUser{
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8000/user-api';
  private authUrl = `${this.baseUrl}/auth`;
  private usersUrl = `${this.baseUrl}/users`;

  private http = inject(HttpClient);
  private router = inject(Router);


  loginUser(credentials: LoginPayload): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.authUrl}/login`, credentials).pipe(

      switchMap(response => {

        this.storeToken(response.accessToken);


        const decodedToken = this.decodeToken(response.accessToken);
        const username = decodedToken?.sub;

        if (!username) {
          return of(response);
        }

        return this.fetchAndStoreUserProfile(username).pipe(

          switchMap(() => of(response))
        );
      })
    );
  }

  registerUser(userData: RegisterPayload): Observable<string> {
    return this.http.post(`${this.authUrl}/register`, userData, { responseType: 'text' });
  }

  updateUserProfile(username: string, userData: Partial<UpdateUser>): Observable<UserProfile> {
  const myHeaders = this.getAuthHeaders();

  return this.http.patch<UserProfile>(
    `${this.usersUrl}/${username}/name`,
    userData,
    { headers: myHeaders }
  ).pipe(
    tap(updatedUser => {
      this.storeUserInfo(updatedUser);
    })
  );
}

  logout(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('currentUser');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getCurrentUser(): UserProfile | null {


    const user = localStorage.getItem('currentUser');
    return user ? JSON.parse(user) : null;
  }


  getUserProfile(username: string): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.usersUrl}/${username}`, { headers: this.getAuthHeaders() });
  }

  getPrimaryRole(): string | null {
    const user = this.getCurrentUser();

    if (!user || !user.roles) {
      return null;
    }

    const roles: string[] = user.roles;

    if (roles.includes('ROLE_ADMIN')) return 'ROLE_ADMIN';
    if (roles.includes('ROLE_AGENT')) return 'ROLE_AGENT';
    if (roles.includes('ROLE_USER')) return 'ROLE_USER';

    return roles.length > 0 ? roles[0] : null; // Fallback
  }

  getToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  getUserId(): string | null {
    return this.getCurrentUser()?.id ?? null;
  }

  getUserName(): string | null {
    return this.getCurrentUser()?.username ?? null;
  }

  getUserFullName(): string | null {
    return this.getCurrentUser()?.name ?? null;
  }

  getUserEmail(): string | null {
    return this.getCurrentUser()?.email ?? null;
  }


  private fetchAndStoreUserProfile(username: string): Observable<UserProfile> {
    return this.getUserProfile(username).pipe(
      tap(userProfile => {
        this.storeUserInfo(userProfile);
      })
    );
  }

  private storeToken(token: string): void {
    localStorage.setItem('accessToken', token);
  }


  private storeUserInfo(user: UserProfile): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  public getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    if (!token) {
      return new HttpHeaders();
    }
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  private decodeToken(token: string): any {
    try {
      const payload = token.split('.')[1];
      const decodedPayload = atob(payload);
      return JSON.parse(decodedPayload);
    } catch (error) {
      return null;
    }
  }
}
