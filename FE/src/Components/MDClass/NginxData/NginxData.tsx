import LocationsData, {
  Locations,
} from 'Components/MDClass/LocationsData/LocationsData';

export interface HttpsOption {
  sslCertificate: string;
  sslCertificateKey: string;
  sslPath: string;
}

export interface Nginx {
  domainUrl: string;
  locations: Locations[];
  httpsOption: HttpsOption;
  https: boolean;
}

export default class NginxData {
  public domainUrl: string;

  public locations: Locations[];

  public httpsOption: HttpsOption;

  public https: false;

  constructor() {
    this.domainUrl = '';
    this.locations = [new LocationsData()];
    this.httpsOption = {
      sslCertificate: '',
      sslCertificateKey: '',
      sslPath: '',
    };
    this.https = false;
  }
}
