import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth-service';

export const loginGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    alert('You are already logged in.');
    const userRole = authService.getPrimaryRole();
    switch (userRole) {
      case 'ROLE_ADMIN':
        router.navigate(['/admin/dashboard']);
        break;
      case 'ROLE_AGENT':
        router.navigate(['/agent/dashboard']);
        break;
      default:
        router.navigate(['/']);
        break;
    }
    return false;
  } else {
    return true;
  }
};
