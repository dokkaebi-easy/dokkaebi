import create from 'zustand';

interface RunData {
  run: number;
  setRun: (data: number) => void;

  intervals: NodeJS.Timer[];
  setIntervals: (data: NodeJS.Timer[]) => void;
}

export const useRunStore = create<RunData>((set) => ({
  run: 0,
  setRun: (data) => set(() => ({ run: data })),

  intervals: [],
  setIntervals: (data) => set(() => ({ intervals: data })),
}));
