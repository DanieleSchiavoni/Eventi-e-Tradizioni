export interface Event {
  id?: number;
  title: string;
  description?: string;
  startDate: string;   // ISO 8601
  endDate?: string;    // ISO 8601
  locationId?: number;
  locationName?: string;
  categoryId?: number;
  categoryName?: string;
  categoryIcon?: string;
  imageUrl?: string;
  organizerName?: string;
}
