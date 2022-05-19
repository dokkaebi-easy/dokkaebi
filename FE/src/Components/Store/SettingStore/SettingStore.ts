import create from 'zustand';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';
import NginxData, { Nginx } from 'Components/MDClass/NginxData/NginxData';

import DBData, { DB } from 'Components/MDClass/DBData/DBData';

interface SettingData {
  projectId: number;
  setProjectId: (id: number) => void;

  projectName: string;
  setProjectName: (name: string) => void;

  buildConfigs: Build[];
  setBuildConfigs: (buildDatas: Build[]) => void;

  dbConfigs: DB[];
  setDBConfigs: (dbDatas: DB[]) => void;

  gitConfig: Git;
  setGitConfig: (gitData: Git) => void;

  nginxConfig: Nginx;
  setNginxConfig: (nginxData: Nginx) => void;
}

export const useSettingStore = create<SettingData>((set) => ({
  projectId: 0,
  setProjectId: (id) =>
    set(() => ({
      projectId: id,
    })),
  projectName: '',
  setProjectName: (name) =>
    set(() => ({
      projectName: name,
    })),
  buildConfigs: [new BuildData()],
  setBuildConfigs: (buildDatas) =>
    set(() => ({
      buildConfigs: buildDatas,
    })),
  dbConfigs: [new DBData()],
  setDBConfigs: (dbDatas) =>
    set(() => ({
      dbConfigs: dbDatas,
    })),
  gitConfig: new GitData(),
  setGitConfig: (gitData) =>
    set(() => ({
      gitConfig: gitData,
    })),
  nginxConfig: new NginxData(),
  setNginxConfig: (nginxData) =>
    set(() => ({
      nginxConfig: nginxData,
    })),
}));
