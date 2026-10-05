export interface Location {
  id?: number;
  name: string;
  description?: string;
  address?: string;
  latitude: number;
  longitude: number;
  imageUrl?: string;
  categoriaId?: number;
  categoriaName?: string;
  categoriaIcon?: string;
}
