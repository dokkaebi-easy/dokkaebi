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
    this.buildNumber = 0;
    this.buildStateId = 0;
    this.registDate = '';
    this.state = {
      build: '',
      pull: '',
      run: '',
    };
  }
}
