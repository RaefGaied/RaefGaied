import dayjs from 'dayjs/esm';

export interface IPaiement {
  id: number;
  montant?: number | null;
  datePaiement?: dayjs.Dayjs | null;
  methodePaiement?: string | null;
  token?: string | null;
  description?: string | null;
}

export type NewPaiement = Omit<IPaiement, 'id'> & { id: null };
