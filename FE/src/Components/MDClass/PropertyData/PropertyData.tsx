export interface Property {
  property: string;
  first: string;
  second: string;
}

export default class PropertyData {
  public property: string;

  public first: string;

  public second: string;

  constructor() {
    this.property = '';
    this.first = '';
    this.second = '';
  }
}
