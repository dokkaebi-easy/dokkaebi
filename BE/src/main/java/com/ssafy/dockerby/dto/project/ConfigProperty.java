package com.ssafy.dockerby.dto.project;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigProperty {

  private String property;
  private String data;

  public static ConfigProperty of(String property, String data) {
    return new ConfigProperty(property, data);
  }

  public boolean isEmpty() {
    return property.isBlank() || data.isBlank();
  }
}