import LocationsData, {
  Locations,
} from 'Components/MDClass/LocationsData/LocationsData';

export interface HttpsOption {
  sslCertificate: string;
  sslCertificateKey: string;
  sslPath: string;
}

export interface Nginx {
  domains: string[];
  locations: Locations[];
  httpsOption: HttpsOption;
  https: boolean;
}

export default class NginxData {
  public domains: string[];

  public locations: Locations[];

  public httpsOption: HttpsOption;

  public https: false;

  constructor() {
    this.domains = [''];
    this.locations = [new LocationsData()];
    this.httpsOption = {
      sslCertificate: '',
      sslCertificateKey: '',
      sslPath: '',
    };
    this.https = false;
  }
}
