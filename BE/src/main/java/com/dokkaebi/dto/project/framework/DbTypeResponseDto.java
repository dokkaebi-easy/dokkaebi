package com.dokkaebi.dto.project.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DbTypeResponseDto {
  private final Long id;
  private final String name;
}
