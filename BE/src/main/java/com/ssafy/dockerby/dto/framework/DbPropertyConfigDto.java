package com.ssafy.dockerby.dto.framework;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbPropertyConfigDto {

  private List<String> properties;
  private String init;
  private String volume;

}
