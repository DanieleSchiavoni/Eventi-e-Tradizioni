import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { EventService } from '../../core/services/event.service';
import { NoticeService } from '../../core/services/notice.service';
import { Event } from '../../core/models/event.model';
import { Notice } from '../../core/models/notice.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  featuredEvents: Event[] = [];
  urgentNotices: Notice[] = [];
  loading = true;

  constructor(private eventService: EventService, private noticeService: NoticeService) {}

  ngOnInit(): void {
    forkJoin({
      events: this.eventService.getUpcoming(),
      notices: this.noticeService.getAll()
    }).subscribe({
      next: ({ events, notices }) => {
        this.featuredEvents = events.slice(0, 3);
        this.urgentNotices = notices.filter(n => n.priorita === 'ALTA').slice(0, 3);
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }
}
