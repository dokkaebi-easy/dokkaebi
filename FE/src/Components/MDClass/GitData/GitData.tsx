export interface Git {
  name: string;
  hostUrl: string;
  credentials: string;
  secretToken: string;
  projectID: string;
  repositoryUrl: string;
  repositoryCredentials: string;
  branchSpecifier: string;
}

export default class GitData {
  public name: string;

  public hostUrl: string;

  public credentials: string;

  public secretToken: string;

  public projectID: string;

  public repositoryUrl: string;

  public repositoryCredentials: string;

  public branchSpecifier: string;

  constructor() {
    this.name = '';
    this.hostUrl = '';
    this.credentials = '';
    this.secretToken = '';
    this.projectID = '';
    this.repositoryUrl = '';
    this.repositoryCredentials = '';
    this.branchSpecifier = '';
  }
}
