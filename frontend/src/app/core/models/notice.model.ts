export type Priorita = 'ALTA' | 'MEDIA' | 'BASSE';

export interface Notice {
  id?: number;
  title: string;
  content: string;
  priorita: Priorita;
  publishedAt?: string; // ISO 8601
  eventoId?: number;    // collegamento OPZIONALE a un evento
  eventoTitle?: string;
}
