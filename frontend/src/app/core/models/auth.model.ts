export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  nome: string;
  cognome: string;
  email: string;
  password: string;
}

export interface JwtResponse {
  type: string;
  token: string;
  id: number;
  username: string; // contiene l'email (Utente non ha un campo username separato)
  email: string;
  role: string; // "RUOLO_ADMIN" | "RUOLO_USER"
}
