export interface DBproperty {
  data: string;
  property: string;
}

export interface DB {
  dumpLocation: string;
  frameworkId: number;
  name: string;
  port: string;
  properties: DBproperty[];
  version: string;
}

export default class DBData {
  public dumpLocation: string;

  public frameworkId: number;

  public name: string;

  public port: string;

  public properties: DBproperty[];

  public version: string;

  constructor() {
    this.dumpLocation = '';
    this.frameworkId = 0;
    this.name = '';
    this.port = '';
    this.properties = [];
    this.version = '';
  }
}
