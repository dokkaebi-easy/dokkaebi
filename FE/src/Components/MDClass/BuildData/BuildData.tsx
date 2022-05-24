export interface BuildProperty {
  property: string;
  data: string;
}

export interface Build {
  frameworkId: number;
  name: string;
  version: string;
  type: string;
  projectDirectory: string;
  buildPath: string;
  properties: BuildProperty[];
}

export default class BuildData {
  public frameworkId: number;

  public name: string;

  public version: string;

  public type: string;

  public projectDirectory: string;

  public buildPath: string;

  public properties: BuildProperty[];

  constructor() {
    this.frameworkId = 0;
    this.name = '';
    this.version = '';
    this.type = '';
    this.projectDirectory = '';
    this.buildPath = '';
    this.properties = [
      {
        property: '',
        data: '',
      },
    ];
  }
}
