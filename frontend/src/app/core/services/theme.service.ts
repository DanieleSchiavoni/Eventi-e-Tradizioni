import { Injectable, signal, effect } from '@angular/core';

const STORAGE_KEY = 'avetrana-theme';

/**
 * Gestisce il tema chiaro/scuro di tutto il sito usando l'attributo
 * data-bs-theme sull'elemento <html>, supportato nativamente da Bootstrap 5.3+
 * (ricolora automaticamente card, form, tabelle, alert, badge, ecc.).
 * La preferenza viene salvata in localStorage e riletta al prossimo avvio;
 * se non è mai stata scelta, si parte dalla preferenza di sistema del browser.
 */
@Injectable({ providedIn: 'root' })
export class ThemeService {

  isDark = signal<boolean>(this.readInitial());

  constructor() {
    effect(() => {
      const theme = this.isDark() ? 'dark' : 'light';
      document.documentElement.setAttribute('data-bs-theme', theme);
      localStorage.setItem(STORAGE_KEY, theme);
    });
  }

  toggle(): void {
    this.isDark.set(!this.isDark());
  }

  private readInitial(): boolean {
    const stored = localStorage.getItem(STORAGE_KEY);
    if (stored) {
      return stored === 'dark';
    }
    return typeof window !== 'undefined'
      && window.matchMedia
      && window.matchMedia('(prefers-color-scheme: dark)').matches;
  }
}
