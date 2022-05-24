package com.ssafy.dockerby.entity.project.enums;

public enum StateType {
  Processing("빌드중"), Done("실행중") , Failed("실패") , Waiting("대기");

  private final String name;

  StateType(String name) {
    this.name= name;
  }

  public String getName() {
    return name;
  }
}
