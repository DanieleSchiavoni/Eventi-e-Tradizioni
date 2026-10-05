import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EventService } from '../../../core/services/event.service';
import { CategoryService } from '../../../core/services/category.service';
import { Event } from '../../../core/models/event.model';
import { Category } from '../../../core/models/category.model';

@Component({
  selector: 'app-eventi-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './eventi-list.component.html'
})
export class EventiListComponent implements OnInit {

  events: Event[] = [];
  categories: Category[] = [];
  loading = true;

  selectedCategoryId: number | null = null;
  fromDate: string | null = null;
  toDate: string | null = null;

  constructor(private eventService: EventService, private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(cats => this.categories = cats);
    this.loadEvents();
  }

  loadEvents(): void {
    this.loading = true;
    this.eventService.getAll({
      categoryId: this.selectedCategoryId ?? undefined,
      from: this.fromDate ? `${this.fromDate}T00:00:00` : undefined,
      to: this.toDate ? `${this.toDate}T23:59:59` : undefined
    }).subscribe({
      next: (events) => { this.events = events; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  resetFilters(): void {
    this.selectedCategoryId = null;
    this.fromDate = null;
    this.toDate = null;
    this.loadEvents();
  }
}
