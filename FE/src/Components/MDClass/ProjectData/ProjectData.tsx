export interface Project {
  projectId: number;
  projectName: string;
  state: string;
}

export default class ProjectDatas {
  public projectId: number;

  public projectName: string;

  public state: string;

  constructor() {
    this.projectId = 0;
    this.projectName = '';
    this.state = '';
  }
}
