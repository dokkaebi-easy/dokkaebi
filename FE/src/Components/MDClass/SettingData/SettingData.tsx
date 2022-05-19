import BuildData, { Build } from '../BuildData/BuildData';
import GitData, { Git } from '../GitData/GitData';
import NginxData, { Nginx } from '../NginxData/NginxData';
import DBData, { DB } from '../DBData/DBData';

export default class SettingData {
  public projectId: number;

  public projectName: string;

  public buildConfigs: Build[];

  public dbConfig: DB[];

  public gitConfig: Git;

  public nginxConfig: Nginx;

  constructor() {
    this.projectId = 0;
    this.projectName = '';
    this.buildConfigs = [new BuildData()];
    this.dbConfig = [new DBData()];
    this.gitConfig = new GitData();
    this.nginxConfig = new NginxData();
  }
}
