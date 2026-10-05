import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { LocationService } from '../../../core/services/location.service';
import { CategoryService } from '../../../core/services/category.service';
import { Location } from '../../../core/models/location.model';
import { Category } from '../../../core/models/category.model';

@Component({
  selector: 'app-admin-luoghi',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin-luoghi.component.html'
})
export class AdminLuoghiComponent implements OnInit {

  locations: Location[] = [];
  categories: Category[] = [];

  loading = true;
  saving = false;
  errorMessage: string | null = null;
  editingId: number | null = null;
  showForm = false;

  private fb = inject(FormBuilder);

  form = this.fb.group({
    name: ['', Validators.required],
    description: [''],
    address: [''],
    latitude: [null as number | null, [Validators.required]],
    longitude: [null as number | null, [Validators.required]],
    imageUrl: [''],
    categoriaId: [null as number | null]
  });

  constructor(
    private locationService: LocationService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(cats => this.categories = cats);
    this.loadLocations();
  }

  loadLocations(): void {
    this.loading = true;
    this.locationService.getAll().subscribe({
      next: (locations) => { this.locations = locations; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  newLocation(): void {
    this.editingId = null;
    this.form.reset();
    this.showForm = true;
    this.errorMessage = null;
  }

  editLocation(location: Location): void {
    this.editingId = location.id ?? null;
    this.form.patchValue({
      name: location.name,
      description: location.description,
      address: location.address,
      latitude: location.latitude,
      longitude: location.longitude,
      imageUrl: location.imageUrl,
      categoriaId: location.categoriaId ?? null
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
    const payload: Location = {
      name: raw.name!,
      description: raw.description ?? undefined,
      address: raw.address ?? undefined,
      latitude: raw.latitude!,
      longitude: raw.longitude!,
      imageUrl: raw.imageUrl ?? undefined,
      categoriaId: raw.categoriaId ?? undefined
    };

    const request$ = this.editingId
      ? this.locationService.update(this.editingId, payload)
      : this.locationService.create(payload);

    request$.subscribe({
      next: () => {
        this.saving = false;
        this.cancelForm();
        this.loadLocations();
      },
      error: (err) => {
        this.saving = false;
        this.errorMessage = err?.error?.message || 'Impossibile salvare il luogo.';
      }
    });
  }

  deleteLocation(location: Location): void {
    if (!location.id) return;
    if (!confirm(`Eliminare il luogo "${location.name}"? Fallira' se e' ancora referenziato da eventi esistenti.`)) return;

    this.locationService.delete(location.id).subscribe({
      next: () => this.loadLocations(),
      error: (err) => this.errorMessage = err?.error?.message || 'Impossibile eliminare il luogo (probabilmente e\' ancora in uso).'
    });
  }
}
