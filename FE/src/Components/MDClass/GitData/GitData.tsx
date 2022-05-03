export interface Git {
  hostUrl: string;
  accessTokenId: number;
  secretToken: string;
  gitProjectId: number;
  repositoryUrl: string;
  branchName: string;
}

export default class GitData {
  public hostUrl: string;

  public accessTokenId: number;

  public secretToken: string;

  public gitProjectId: number;

  public repositoryUrl: string;

  public branchName: string;

  constructor() {
    this.hostUrl = '';
    this.accessTokenId = 0;
    this.secretToken = '';
    this.gitProjectId = 0;
    this.repositoryUrl = '';
    this.branchName = '';
  }
}
