import create from 'zustand';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface DropdownData {
  framworkandLib: ResponseIdName[];
  setFramworkandLib: (data: ResponseIdName[]) => void;

  account: ResponseIdName[];
  setAccount: (data: ResponseIdName[]) => void;

  accessToken: ResponseIdName[];
  setAccessToken: (data: ResponseIdName[]) => void;
}

export const useStore = create<DropdownData>((set) => ({
  framworkandLib: [],
  setFramworkandLib: (data) => set(() => ({ framworkandLib: data })),

  account: [],
  setAccount: (data) => set(() => ({ account: data })),

  accessToken: [],
  setAccessToken: (data) => set(() => ({ accessToken: data })),
}));
