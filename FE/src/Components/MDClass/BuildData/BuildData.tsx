import PropertyData, { Property } from '../PropertyData/PropertyData';

export interface Build {
  frameworkName: string;
  name: string;
  version: string;
  type: string;
  projectDirectory: string;
  buildPath: string;
  propertys: Property[];
}

export default class BuildData {
  public frameworkName: string;

  public name: string;

  public version: string;

  public type: string;

  public projectDirectory: string;

  public buildPath: string;

  public propertys: Property[];

  constructor() {
    this.frameworkName = '';
    this.name = '';
    this.version = '';
    this.type = '';
    this.projectDirectory = '';
    this.buildPath = '';
    this.propertys = [new PropertyData()];
  }
}
