import create from 'zustand';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import GitData, { Git } from 'Components/MDClass/GitData/GitData';
import NginxData, { Nginx } from 'Components/MDClass/NginxData/NginxData';

interface SettingData {
  projectName: string;
  setProjectName: (name: string) => void;

  buildConfig: Build[];
  setBuildConfig: (buildDatas: Build[]) => void;

  gitConfig: Git;
  setGitConfig: (gitData: Git) => void;

  nginxConfig: Nginx;
  setNginxConfig: (nginxData: Nginx) => void;
}

export const useStore = create<SettingData>((set) => ({
  projectName: '',
  setProjectName: (name) =>
    set((state) => {
      state.projectName = name;
      return state;
    }),
  buildConfig: [new BuildData()],
  setBuildConfig: (buildData) =>
    set((state) => {
      state.buildConfig = buildData;
      return state;
    }),
  gitConfig: new GitData(),
  setGitConfig: (gitData) =>
    set((state) => {
      state.gitConfig = gitData;
      return state;
    }),
  nginxConfig: new NginxData(),
  setNginxConfig: (nginxData) =>
    set((state) => {
      state.nginxConfig = nginxData;
      return state;
    }),
}));
