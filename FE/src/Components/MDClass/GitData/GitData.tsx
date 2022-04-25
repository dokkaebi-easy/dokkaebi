export interface Git {
  name: string;
  hosturl: string;
  credentials: string;
  secrettoken: string;
  projectID: string;
  repositoryurl: string;
  repositorycredentials: string;
  branchspecifier: string;
}

export default class GitData {
  public name: string;

  public hosturl: string;

  public credentials: string;

  public secrettoken: string;

  public projectID: string;

  public repositoryurl: string;

  public repositorycredentials: string;

  public branchspecifier: string;

  constructor() {
    this.name = '';
    this.hosturl = '';
    this.credentials = '';
    this.secrettoken = '';
    this.projectID = '';
    this.repositoryurl = '';
    this.repositorycredentials = '';
    this.branchspecifier = '';
  }
}
