import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  dateT?: Moment;
  montant?: number;
  dateR?: Moment;
  commission?: number;
  commSystem?: number;
  commExp?: number;
  tax2?: number;
  statut?: string;
  code?: string;
  commretrait?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public dateT?: Moment,
    public montant?: number,
    public dateR?: Moment,
    public commission?: number,
    public commSystem?: number,
    public commExp?: number,
    public tax2?: number,
    public statut?: string,
    public code?: string,
    public commretrait?: number
  ) {}
}
