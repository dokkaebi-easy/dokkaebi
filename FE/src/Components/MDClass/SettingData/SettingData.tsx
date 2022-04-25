import BuildData, { Build } from '../BuildData/BuildData';
import GitData, { Git } from '../GitData/GitData';
import NginxData, { Nginx } from '../NginxData/NginxData';

export default class SettingData {
  public projectName: string;

  public buildConfigs: Build;

  public gitConfigs: Git;

  public nginxConfigs: Nginx;

  constructor() {
    this.projectName = '';
    this.buildConfigs = new BuildData();
    this.gitConfigs = new GitData();
    this.nginxConfigs = new NginxData();
  }
}
