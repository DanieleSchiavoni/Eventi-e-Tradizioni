import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { NoticeService } from '../../../core/services/notice.service';
import { EventService } from '../../../core/services/event.service';
import { Notice, Priorita } from '../../../core/models/notice.model';
import { Event } from '../../../core/models/event.model';

@Component({
  selector: 'app-admin-notices',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin-notices.component.html'
})
export class AdminNoticesComponent implements OnInit {

  notices: Notice[] = [];
  events: Event[] = [];

  loading = true;
  saving = false;
  errorMessage: string | null = null;
  editingId: number | null = null;
  showForm = false;

  private fb = inject(FormBuilder);

  form = this.fb.group({
    title: ['', Validators.required],
    content: ['', Validators.required],
    priorita: ['MEDIA' as Priorita, Validators.required],
    eventoId: [null as number | null]
  });

  constructor(private noticeService: NoticeService, private eventService: EventService) {}

  ngOnInit(): void {
    this.eventService.getAll().subscribe(events => this.events = events);
    this.loadNotices();
  }

  loadNotices(): void {
    this.loading = true;
    this.noticeService.getAll().subscribe({
      next: (notices) => { this.notices = notices; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  newNotice(): void {
    this.editingId = null;
    this.form.reset({ priorita: 'MEDIA', eventoId: null });
    this.showForm = true;
    this.errorMessage = null;
  }

  editNotice(notice: Notice): void {
    this.editingId = notice.id ?? null;
    this.form.patchValue({
      title: notice.title,
      content: notice.content,
      priorita: notice.priorita,
      eventoId: notice.eventoId ?? null
    });
    this.showForm = true;
    this.errorMessage = null;
  }

  cancelForm(): void {
    this.showForm = false;
    this.editingId = null;
    this.form.reset();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.errorMessage = null;

    const raw = this.form.getRawValue();
    const payload: Notice = {
      title: raw.title!,
      content: raw.content!,
      priorita: raw.priorita!,
      eventoId: raw.eventoId ?? undefined
    };

    const request$ = this.editingId
      ? this.noticeService.update(this.editingId, payload)
      : this.noticeService.create(payload);

    request$.subscribe({
      next: () => {
        this.saving = false;
        this.cancelForm();
        this.loadNotices();
      },
      error: (err) => {
        this.saving = false;
        this.errorMessage = err?.error?.message || 'Impossibile salvare l\'avviso.';
      }
    });
  }

  deleteNotice(notice: Notice): void {
    if (!notice.id) return;
    if (!confirm(`Eliminare l'avviso "${notice.title}"?`)) return;

    this.noticeService.delete(notice.id).subscribe({
      next: () => this.loadNotices(),
      error: (err) => this.errorMessage = err?.error?.message || 'Impossibile eliminare l\'avviso.'
    });
  }

  priorityBadgeClass(priorita: Priorita): string {
    switch (priorita) {
      case 'ALTA': return 'badge-priority-high';
      case 'MEDIA': return 'badge-priority-medium';
      default: return 'badge-priority-low';
    }
  }
}
