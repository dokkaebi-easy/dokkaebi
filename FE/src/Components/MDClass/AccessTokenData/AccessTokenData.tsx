export interface AccessToken {
  accessToken: string;
  name: string;
}

export default class AccessTokenData {
  public accessToken: string;

  public name: string;

  constructor() {
    this.accessToken = '';
    this.name = '';
  }
}
