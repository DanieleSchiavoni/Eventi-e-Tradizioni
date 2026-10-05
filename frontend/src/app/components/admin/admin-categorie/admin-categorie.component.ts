import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { CategoryService } from '../../../core/services/category.service';
import { Category } from '../../../core/models/category.model';

@Component({
  selector: 'app-admin-categorie',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin-categorie.component.html'
})
export class AdminCategorieComponent implements OnInit {

  categories: Category[] = [];

  loading = true;
  saving = false;
  errorMessage: string | null = null;
  editingId: number | null = null;
  showForm = false;

  private fb = inject(FormBuilder);

  form = this.fb.group({
    nome: ['', Validators.required],
    icona: ['']
  });

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.loading = true;
    this.categoryService.getAll().subscribe({
      next: (categories) => { this.categories = categories; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  newCategory(): void {
    this.editingId = null;
    this.form.reset();
    this.showForm = true;
    this.errorMessage = null;
  }

  editCategory(category: Category): void {
    this.editingId = category.id ?? null;
    this.form.patchValue({
      nome: category.nome,
      icona: category.icona
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
    const payload: Category = {
      nome: raw.nome!,
      icona: raw.icona ?? undefined
    };

    const request$ = this.editingId
      ? this.categoryService.update(this.editingId, payload)
      : this.categoryService.create(payload);

    request$.subscribe({
      next: () => {
        this.saving = false;
        this.cancelForm();
        this.loadCategories();
      },
      error: (err) => {
        this.saving = false;
        this.errorMessage = err?.error?.message || 'Impossibile salvare la categoria.';
      }
    });
  }

  deleteCategory(category: Category): void {
    if (!category.id) return;
    if (!confirm(`Eliminare la categoria "${category.nome}"? Falliraà se e' ancora usata da luoghi o eventi.`)) return;

    this.categoryService.delete(category.id).subscribe({
      next: () => this.loadCategories(),
      error: (err) => this.errorMessage = err?.error?.message || 'Impossibile eliminare la categoria (probabilmente e\' ancora in uso).'
    });
  }
}
