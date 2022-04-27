import create from 'zustand';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';
import NginxData, { Nginx } from 'Components/MDClass/NginxData/NginxData';

interface SettingData {
  projectName: string;
  setProjectName: (name: string) => void;

  buildConfigs: Build[];
  setBuildConfigs: (buildDatas: Build[]) => void;

  gitConfig: Git;
  setGitConfig: (gitData: Git) => void;

  nginxConfig: Nginx;
  setNginxConfig: (nginxData: Nginx) => void;
}

export const useStore = create<SettingData>((set) => ({
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
