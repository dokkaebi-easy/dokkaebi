export interface Locations {
  location: string;
  url: string;
}

export default class LocationsData {
  public location: string;

  public url: string;

  constructor() {
    this.location = '';
    this.url = '';
  }
}
