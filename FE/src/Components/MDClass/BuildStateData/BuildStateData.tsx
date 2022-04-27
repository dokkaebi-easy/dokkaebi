export interface State {
  build: string;
  pull: string;
  run: string;
}

export interface BuildState {
  buildNumber: number;
  buildStateId: number;
  registDate: string;
  state: State;
}

export default class BuildStateData {
  public buildNumber: number;

  public buildStateId: number;

  public registDate: string;

  public state: State;

  constructor() {
    this.buildNumber = 1;
    this.buildStateId = 1;
    this.registDate = '1';
    this.state = {
      build: '1',
      pull: '1',
      run: '1',
    };
  }
}
