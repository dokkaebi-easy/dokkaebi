import BuildData, { Build } from '../BuildData/BuildData';
import GitData, { Git } from '../GitData/GitData';
import NginxData, { Nginx } from '../NginxData/NginxData';

export default class SettingData {
  public projectId: number;

  public projectName: string;

  public buildConfigs: Build;

  public gitConfig: Git;

  public nginxConfig: Nginx;

  constructor() {
    this.projectId = 0;
    this.projectName = '';
    this.buildConfigs = new BuildData();
    this.gitConfig = new GitData();
    this.nginxConfig = new NginxData();
  }
}
