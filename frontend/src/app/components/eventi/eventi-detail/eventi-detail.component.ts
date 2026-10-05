import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EventService } from '../../../core/services/event.service';
import { Event } from '../../../core/models/event.model';

@Component({
  selector: 'app-eventi-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './eventi-detail.component.html'
})
export class EventiDetailComponent implements OnInit {

  event: Event | null = null;
  loading = true;
  notFound = false;

  constructor(private route: ActivatedRoute, private eventService: EventService) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.getById(id).subscribe({
      next: (event) => { this.event = event; this.loading = false; },
      error: () => { this.notFound = true; this.loading = false; }
    });
  }
}
