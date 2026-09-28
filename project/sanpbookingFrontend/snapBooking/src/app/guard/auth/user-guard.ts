import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth-service';


export const userGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn() && authService.getPrimaryRole() === 'ROLE_USER') {
    return true;
  } else {
    alert('Access Denied: You must be logged in as a customer to view this page.');
    router.navigate(['/login']);
    return false;
  }
};