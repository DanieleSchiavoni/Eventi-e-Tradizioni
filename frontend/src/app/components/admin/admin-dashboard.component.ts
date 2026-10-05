import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminEventiComponent } from './admin-eventi/admin-eventi.component';
import { AdminLuoghiComponent } from './admin-luoghi/admin-luoghi.component';
import { AdminCategorieComponent } from './admin-categorie/admin-categorie.component';
import { AdminNoticesComponent } from './admin-notices/admin-notices.component';

type AdminTab = 'eventi' | 'luoghi' | 'categorie' | 'avvisi';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, AdminEventiComponent, AdminLuoghiComponent, AdminCategorieComponent, AdminNoticesComponent],
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent {
  activeTab: AdminTab = 'eventi';

  setTab(tab: AdminTab): void {
    this.activeTab = tab;
  }
}
