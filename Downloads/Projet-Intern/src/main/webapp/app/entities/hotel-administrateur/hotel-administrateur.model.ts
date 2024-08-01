export interface IHotelAdministrateur {
  id: number;
  nom?: string | null;
  email?: string | null;
  motDePasse?: string | null;
}

export type NewHotelAdministrateur = Omit<IHotelAdministrateur, 'id'> & { id: null };
