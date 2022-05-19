export interface Project {
  projectId: number;
  projectName: string;
  state: string;
  lastSuccessDate: string;
  lastFailDate: string;
  lastDuration: string;
}

export default class ProjectDatas {
  public projectId: number;

  public projectName: string;

  public state: string;

  public lastSuccessDate: string;

  public lastFailDate: string;

  public lastDuration: string;

  constructor() {
    this.projectId = 0;
    this.projectName = '';
    this.state = '';
    this.lastSuccessDate = '';
    this.lastFailDate = '';
    this.lastDuration = '';
  }
}
