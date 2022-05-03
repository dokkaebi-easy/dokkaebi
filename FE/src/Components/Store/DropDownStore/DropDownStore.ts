import create from 'zustand';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface DropdownData {
  framwork: ResponseIdName[];
  setFramwork: (data: ResponseIdName[]) => void;

  db: ResponseIdName[];
  setDB: (data: ResponseIdName[]) => void;

  account: ResponseIdName[];
  setAccount: (data: ResponseIdName[]) => void;

  accessToken: ResponseIdName[];
  setAccessToken: (data: ResponseIdName[]) => void;
}

export const useDropdownStore = create<DropdownData>((set) => ({
  framwork: [],
  setFramwork: (data) => set(() => ({ framwork: data })),

  db: [],
  setDB: (data) => set(() => ({ db: data })),

  account: [],
  setAccount: (data) => set(() => ({ account: data })),

  accessToken: [],
  setAccessToken: (data) => set(() => ({ accessToken: data })),
}));
