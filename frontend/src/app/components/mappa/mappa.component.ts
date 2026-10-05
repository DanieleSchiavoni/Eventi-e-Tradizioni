import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import * as L from 'leaflet';
import { LocationService } from '../../core/services/location.service';
import { CategoryService } from '../../core/services/category.service';
import { Location } from '../../core/models/location.model';
import { Category } from '../../core/models/category.model';

// Fix icone Leaflet di default (problema noto con i bundler)
const iconDefault = L.icon({
  iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = iconDefault;

// Centro approssimativo di Avetrana (TA)
const AVETRANA_CENTER: L.LatLngExpression = [40.3815, 17.7188];

@Component({
  selector: 'app-mappa',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mappa.component.html'
})
export class MappaComponent implements OnInit, AfterViewInit, OnDestroy {

  private map: L.Map | undefined;
  private markers: L.Marker[] = [];

  locations: Location[] = [];
  categories: Category[] = [];
  selectedCategoryId: number | null = null;
  loading = true;

  constructor(private locationService: LocationService, private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(cats => this.categories = cats);
  }

  ngAfterViewInit(): void {
    this.initMap();
    this.loadLocations();
  }

  ngOnDestroy(): void {
    this.map?.remove();
  }

  private initMap(): void {
    this.map = L.map('map').setView(AVETRANA_CENTER, 15);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      maxZoom: 19
    }).addTo(this.map);
  }

  loadLocations(): void {
    this.loading = true;
    this.locationService.getAll(this.selectedCategoryId ?? undefined).subscribe({
      next: (locations) => {
        this.locations = locations;
        this.renderMarkers();
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  private renderMarkers(): void {
    if (!this.map) return;

    // Rimuove i marker precedenti
    this.markers.forEach(m => m.remove());
    this.markers = [];

    this.locations.forEach(loc => {
      const marker = L.marker([loc.latitude, loc.longitude]).addTo(this.map!);
      const popupContent = `
        <div style="max-width:220px;">
          ${loc.imageUrl ? `<img src="${loc.imageUrl}" style="width:100%;height:100px;object-fit:cover;border-radius:4px;margin-bottom:6px;" />` : ''}
          <h6 style="margin-bottom:4px;">${loc.name}</h6>
          ${loc.categoriaName ? `<span class="badge bg-secondary" style="font-size:11px;">${loc.categoriaName}</span>` : ''}
          <p style="font-size:13px;margin-top:6px;margin-bottom:2px;">${loc.description ?? ''}</p>
          ${loc.address ? `<small class="text-muted">${loc.address}</small>` : ''}
        </div>
      `;
      marker.bindPopup(popupContent);
      this.markers.push(marker);
    });
  }

  focusLocation(loc: Location): void {
    if (!this.map) return;
    this.map.setView([loc.latitude, loc.longitude], 18);
    const marker = this.markers.find(m =>
      m.getLatLng().lat === loc.latitude && m.getLatLng().lng === loc.longitude);
    marker?.openPopup();
  }
}
