import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

// Necessario per usare il pipe "date" con locale 'it' (es. date:'d MMMM y':'':'it')
// in tutta l'app: senza questa registrazione il pipe lancia un errore a runtime
// e le celle/righe che lo usano possono non renderizzarsi correttamente.
registerLocaleData(localeIt);

bootstrapApplication(AppComponent, appConfig)
  .catch(err => console.error(err));
