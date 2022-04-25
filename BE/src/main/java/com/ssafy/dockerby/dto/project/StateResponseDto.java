package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.project.enums.BuildType;
import com.ssafy.dockerby.entity.project.enums.StateType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateResponseDto {
  @NonNull
  private Long projectId;

  @Enumerated(value = EnumType.STRING)
  private BuildType buildType;

  @Enumerated(value = EnumType.STRING)
  private StateType stateType;

}
