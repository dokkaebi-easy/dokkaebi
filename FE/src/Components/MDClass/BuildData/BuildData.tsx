import PropertyData, { Property } from '../PropertyData/PropertyData';

export interface Build {
  frameworkId: number;
  name: string;
  version: string;
  type: string;
  projectDirectory: string;
  buildPath: string;
  properties: Property[];
}

export default class BuildData {
  public frameworkId: number;

  public name: string;

  public version: string;

  public type: string;

  public projectDirectory: string;

  public buildPath: string;

  public properties: Property[];

  constructor() {
    this.frameworkId = -1;
    this.name = '';
    this.version = '';
    this.type = '';
    this.projectDirectory = '';
    this.buildPath = '';
    this.properties = [new PropertyData()];
  }
}
