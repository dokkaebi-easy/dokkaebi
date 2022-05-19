export interface State {
  buildStateId: number;
  buildNumber: number;
  buildType: string;
  stateType: string;
  registDate: string;
  lastModifiedDate: string;
}

export interface BuildState {
  buildNumber: number;
  registDate: string;
  buildTotalDetailDtos: State[];
}

export default class BuildStateData {
  public buildNumber: number;

  public registDate: string;

  public buildTotalDetailDtos: State[];

  constructor() {
    this.buildNumber = 0;
    this.registDate = '';
    this.buildTotalDetailDtos = [];
  }
}
