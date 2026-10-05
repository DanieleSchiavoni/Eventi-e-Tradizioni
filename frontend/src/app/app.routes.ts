import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'eventi',
    loadComponent: () => import('./components/eventi/eventi-list/eventi-list.component').then(m => m.EventiListComponent)
  },
  {
    path: 'eventi/:id',
    loadComponent: () => import('./components/eventi/eventi-detail/eventi-detail.component').then(m => m.EventiDetailComponent)
  },
  {
    path: 'mappa',
    loadComponent: () => import('./components/mappa/mappa.component').then(m => m.MappaComponent)
  },
  {
    path: 'avvisi',
    loadComponent: () => import('./components/avvisi/avvisi.component').then(m => m.AvvisiComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./components/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./components/auth/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'admin',
    loadComponent: () => import('./components/admin/admin-dashboard.component').then(m => m.AdminDashboardComponent),
    canActivate: [authGuard, adminGuard]
  },
  { path: '**', redirectTo: '' }
];
