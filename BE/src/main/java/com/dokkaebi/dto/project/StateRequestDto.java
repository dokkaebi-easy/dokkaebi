package com.dokkaebi.dto.project;

import com.dokkaebi.entity.project.enums.BuildType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateRequestDto {

  @NonNull
  private Long projectId;

  @Enumerated(value = EnumType.STRING)
  private BuildType buildType;

  @Override
  public String toString() {
    return "StateRequestDto{" +
      "projectId=" + projectId +
      ", buildType=" + buildType +
      '}';
  }
}
