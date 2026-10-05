import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Notice } from '../models/notice.model';

@Injectable({ providedIn: 'root' })
export class NoticeService {

  private readonly apiUrl = `${environment.apiUrl}/notices`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Notice[]> {
    return this.http.get<Notice[]>(this.apiUrl);
  }

  getById(id: number): Observable<Notice> {
    return this.http.get<Notice>(`${this.apiUrl}/${id}`);
  }

  create(notice: Notice): Observable<Notice> {
    return this.http.post<Notice>(this.apiUrl, notice);
  }

  update(id: number, notice: Notice): Observable<Notice> {
    return this.http.put<Notice>(`${this.apiUrl}/${id}`, notice);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
