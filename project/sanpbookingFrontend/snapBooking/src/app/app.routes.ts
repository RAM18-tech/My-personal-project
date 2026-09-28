import { Routes } from '@angular/router';
import { HomePage } from './home/home-page/home-page';
import { PackagesList } from './packages/packages-list/packages-list';
import { ManagePackages } from './managePackages/manage-packages/manage-packages';
import { AddPackages } from './managePackages/add-packages/add-packages';
import { PackageDetail } from './packages/package-detail/package-detail';
import { AboutUs } from './pages/about-us/about-us';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { loginGuard } from './guard/auth/login-guard';
import { authGuard } from './guard/auth/auth-guard';
import { adminGuard } from './guard/auth/admin-guard';
import { AdminDashboard } from './admin/admin-dashboard/admin-dashboard';
import { agentGuard } from './guard/auth/agent-guard';
import { userGuard } from './guard/auth/user-guard';
import { MyBookings } from './user/my-bookings/my-bookings';
import { ProfileComponent } from './user/profile/profile';
import { PaymentComponent } from './payment/payment/payment';
import { InsuranceComponent } from './insurance/insurance/insurance';

export const routes: Routes = [
  { path: '', component: HomePage },
  { path: 'home', component: HomePage },
  { path: 'packages', component: PackagesList },
  { path: 'about', component: AboutUs },
  { path: 'insurance', component: InsuranceComponent },

  { 
    path: 'agent/dashboard', 
    component: ManagePackages,
    canActivate: [agentGuard] 
  },
  { 
    path: 'agent/add-package', 
    component: AddPackages,
    canActivate: [agentGuard]
  },
  { 
    path: 'agent/edit-package/:id', 
    component: AddPackages,
    canActivate: [agentGuard] 
  },

  {
    path: 'admin/dashboard',
    component: AdminDashboard,
    canActivate: [authGuard, adminGuard]
  },


  { 
    path: 'my-bookings', 
    component: MyBookings,
    canActivate: [userGuard]
  },


  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [userGuard]
  },

  { path: 'payment/:bookingId', component: PaymentComponent, canActivate: [userGuard] },

  { path: 'login', component: Login, canActivate: [loginGuard] },
  { path: 'register', component: Register, canActivate: [loginGuard] },

  { path: 'packages/:id', component: PackageDetail },
];
