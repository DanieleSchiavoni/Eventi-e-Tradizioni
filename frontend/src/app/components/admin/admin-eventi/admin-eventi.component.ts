import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, ReactiveFormsModule, FormBuilder, ValidationErrors, Validators } from '@angular/forms';
import { EventService } from '../../../core/services/event.service';
import { LocationService } from '../../../core/services/location.service';
import { CategoryService } from '../../../core/services/category.service';
import { Event } from '../../../core/models/event.model';
import { Location } from '../../../core/models/location.model';
import { Category } from '../../../core/models/category.model';

/**
 * Validator cross-field: se sono compilate sia data inizio che data fine,
 * la fine deve essere successiva (o uguale) all'inizio.
 * Applicato a livello di FormGroup perché coinvolge due controlli insieme.
 */
function dateRangeValidator(group: AbstractControl): ValidationErrors | null {
  const start = group.get('startDate')?.value;
  const end = group.get('endDate')?.value;

  if (!start || !end) {
    return null; // la data di fine e' opzionale: nessun controllo se manca
  }

  return new Date(end) < new Date(start) ? { dateRange: true } : null;
}

@Component({
  selector: 'app-admin-eventi',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin-eventi.component.html'
})
export class AdminEventiComponent implements OnInit {

  events: Event[] = [];
  locations: Location[] = [];
  categories: Category[] = [];

  loading = true;
  saving = false;
  errorMessage: string | null = null;
  editingId: number | null = null;
  showForm = false;

  private fb = inject(FormBuilder);

  form = this.fb.group({
    title: ['', Validators.required],
    description: [''],
    startDate: ['', Validators.required],
    endDate: [''],
    locationId: [null as number | null],
    categoryId: [null as number | null],
    imageUrl: [''],
    organizerName: ['']
  }, { validators: dateRangeValidator });

  constructor(
    private eventService: EventService,
    private locationService: LocationService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.locationService.getAll().subscribe(locs => this.locations = locs);
    this.categoryService.getAll().subscribe(cats => this.categories = cats);
    this.loadEvents();
  }

  loadEvents(): void {
    this.loading = true;
    this.eventService.getAll().subscribe({
      next: (events) => { this.events = events; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  newEvent(): void {
    this.editingId = null;
    this.form.reset();
    this.showForm = true;
    this.errorMessage = null;
  }

  editEvent(event: Event): void {
    this.editingId = event.id ?? null;
    this.form.patchValue({
      title: event.title,
      description: event.description,
      startDate: event.startDate?.substring(0, 16), // formato datetime-local
      endDate: event.endDate?.substring(0, 16),
      locationId: event.locationId ?? null,
      categoryId: event.categoryId ?? null,
      imageUrl: event.imageUrl,
      organizerName: event.organizerName
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
    const payload: Event = {
      title: raw.title!,
      description: raw.description ?? undefined,
      startDate: raw.startDate!,
      endDate: raw.endDate || undefined,
      locationId: raw.locationId ?? undefined,
      categoryId: raw.categoryId ?? undefined,
      imageUrl: raw.imageUrl ?? undefined,
      organizerName: raw.organizerName ?? undefined
    };

    const request$ = this.editingId
      ? this.eventService.update(this.editingId, payload)
      : this.eventService.create(payload);

    request$.subscribe({
      next: () => {
        this.saving = false;
        this.cancelForm();
        this.loadEvents();
      },
      error: (err) => {
        this.saving = false;
        this.errorMessage = err?.error?.message || 'Impossibile salvare l\'evento.';
      }
    });
  }

  deleteEvent(event: Event): void {
    if (!event.id) return;
    if (!confirm(`Eliminare l'evento "${event.title}"?`)) return;

    this.eventService.delete(event.id).subscribe({
      next: () => this.loadEvents(),
      error: (err) => this.errorMessage = err?.error?.message || 'Impossibile eliminare l\'evento.'
    });
  }
}
