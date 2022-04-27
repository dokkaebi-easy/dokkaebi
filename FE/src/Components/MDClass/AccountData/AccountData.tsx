export interface Access {
  email: string;
  password: string;
  username: string;
}

export default class AccessData {
  public email: string;

  public password: string;

  public username: string;

  constructor() {
    this.email = '';
    this.password = '';
    this.username = '';
  }
}
