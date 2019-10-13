export interface IClient {
  id?: number;
  numPiece?: string;
  nomComplet?: string;
}

export class Client implements IClient {
  constructor(public id?: number, public numPiece?: string, public nomComplet?: string) {}
}
