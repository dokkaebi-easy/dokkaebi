export interface Git {
  name: string;
  hostUrl: string;
  accessTokenId: number;
  secretToken: string;
  projectId: number;
  repositoryUrl: string;
  accountId: number;
  branchName: string;
}

export default class GitData {
  public name: string;

  public hostUrl: string;

  public accessTokenId: number;

  public secretToken: string;

  public projectId: number;

  public repositoryUrl: string;

  public accountId: number;

  public branchName: string;

  constructor() {
    this.name = '';
    this.hostUrl = '';
    this.accessTokenId = 0;
    this.secretToken = '';
    this.projectId = 0;
    this.repositoryUrl = '';
    this.accountId = 0;
    this.branchName = '';
  }
}
