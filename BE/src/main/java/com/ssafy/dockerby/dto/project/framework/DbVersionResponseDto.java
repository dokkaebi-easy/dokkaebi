package com.ssafy.dockerby.dto.project.framework;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DbVersionResponseDto {

  private final List<String> dbVersion;

  private final List<String> properties;

}
