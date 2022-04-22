import React, { Component } from 'react';

export default class BuildData {
  public name: string;

  public version: string;

  public type: string;

  public port: string[];

  public volume: string[];

  public env: string[];

  public build: string[];

  constructor() {
    this.name = '';
    this.version = '';
    this.type = '';
    this.port = [];
    this.volume = [];
    this.env = [];
    this.build = [];
  }
}
