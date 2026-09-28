import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth-service';


export const agentGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    alert('You must be logged in to view this page.');
    router.navigate(['/login']);
    return false;
  }

  if (authService.getPrimaryRole() === 'ROLE_AGENT') {
    return true;
  } else {
    alert('Access Denied: This page is for Travel Agents only.');
    router.navigate(['/']);
    return false;
  }
};
