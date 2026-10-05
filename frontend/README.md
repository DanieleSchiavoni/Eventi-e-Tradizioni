# Avetrana Eventi & Tradizioni - Frontend

Applicazione Angular (standalone components) stilizzata con Bootstrap 5 per esplorare eventi, tradizioni e punti di interesse di Avetrana.

## Requisiti

- Node.js 18+ e npm
- Angular CLI: `npm install -g @angular/cli`
- Il backend Spring Boot deve essere avviato su `http://localhost:8080` (vedi README del backend)

## Installazione

```bash
npm install
```

## Avvio in sviluppo

```bash
npm start
```

L'app parte su `http://localhost:4200` con hot-reload attivo.

## Build di produzione

```bash
npm run build
```

I file compilati finiscono in `dist/avetrana-eventi-frontend`.

## Struttura principale

```
src/app/
├── core/
│   ├── models/         # Interfacce TypeScript (Event, Location, Category, Notice, Auth)
│   ├── services/       # Servizi HttpClient verso le API REST
│   ├── interceptors/   # Interceptor JWT (allega il token alle richieste)
│   └── guards/         # authGuard / adminGuard per le rotte protette
├── components/
│   ├── home/            # Banner avvisi urgenti + eventi in evidenza
│   ├── eventi/           # Lista filtrabile + dettaglio evento
│   ├── mappa/            # Mappa Leaflet.js con i punti di interesse
│   ├── admin/            # Dashboard admin (form Reactive per Eventi e Avvisi)
│   ├── auth/             # Login / Registrazione
│   └── shared/           # Navbar e Footer
```

## Login demo

Usa l'utente amministratore creato dal backend (via `AdminSeeder` o `data.sql`):
- **email**: `admin@avetrana.it`
- **password**: `Admin123!`

Oppure registra un nuovo utente (ruolo cittadino) dalla pagina di registrazione — richiede nome, cognome, email, password.

## Nota sui nomi di campo (backend org.elis.progettoesempio)

Questo frontend è allineato ai nomi di campo reali del backend, che mescola italiano/inglese:

| Risorsa | Campi in italiano | Campi in inglese |
|---|---|---|
| Categoria | `nome`, `icona` | — |
| Luogo | `categoriaId`, `categoriaName`, `categoriaIcon` | `name`, `description`, `address`, `latitude`, `longitude`, `imageUrl` |
| Evento | — | tutti (`title`, `categoryId`, `categoryName`, ecc.) |
| Avviso | `priorita` (valori: `ALTA`/`MEDIA`/`BASSE`) | `title`, `content`, `publishedAt` |
| Auth | `nome`, `cognome` (solo registrazione) | `email`, `password`, `token`, `role` (valori: `RUOLO_ADMIN`/`RUOLO_USER`) |

Se in futuro uniformi i nomi nel backend, ricordati di aggiornare anche i model TypeScript in `core/models/`.
