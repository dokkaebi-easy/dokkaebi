import create from 'zustand';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';
import NginxData, { Nginx } from 'Components/MDClass/NginxData/NginxData';

interface SettingData {
  projectId: number;
  setProjectId: (id: number) => void;

  projectName: string;
  setProjectName: (name: string) => void;

  buildConfigs: Build[];
  setBuildConfigs: (buildDatas: Build[]) => void;

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
