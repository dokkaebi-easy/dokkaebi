import BuildData, { Build } from '../BuildData/BuildData';
import GitData, { Git } from '../GitData/GitData';
import NginxData, { Nginx } from '../NginxData/NginxData';

export default class SettingData {
  public projectName: string;

  public buildConfigs: Build;

  public gitConfig: Git;

  public nginxConfig: Nginx;

  constructor() {
    this.projectName = '';
    this.buildConfigs = new BuildData();
    this.gitConfig = new GitData();
    this.nginxConfig = new NginxData();
  }
}
