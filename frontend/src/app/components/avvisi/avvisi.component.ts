import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NoticeService } from '../../core/services/notice.service';
import { Notice } from '../../core/models/notice.model';

@Component({
  selector: 'app-avvisi',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './avvisi.component.html'
})
export class AvvisiComponent implements OnInit {

  notices: Notice[] = [];
  loading = true;

  constructor(private noticeService: NoticeService) {}

  ngOnInit(): void {
    // Il backend restituisce gia' gli avvisi ordinati dal piu' recente al piu' vecchio
    this.noticeService.getAll().subscribe({
      next: (notices) => { this.notices = notices; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  priorityBadgeClass(priorita: string): string {
    switch (priorita) {
      case 'ALTA': return 'badge-priority-high';
      case 'MEDIA': return 'badge-priority-medium';
      default: return 'badge-priority-low';
    }
  }

  priorityLabel(priorita: string): string {
    switch (priorita) {
      case 'ALTA': return 'Urgente';
      case 'MEDIA': return 'Media priorita\'';
      default: return 'Informativo';
    }
  }
}
