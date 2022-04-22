package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.project.enums.BuildType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StateRequestDto {

  @NonNull
  private Long projectId;

  @Enumerated(value = EnumType.STRING)
  private BuildType stateType;

  @Override
  public String toString() {
    return "StateRequestDto{" +
      "projectId=" + projectId +
      ", stateType=" + stateType +
      '}';
  }
}
