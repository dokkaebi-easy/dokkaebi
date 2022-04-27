package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.project.enums.StateType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateDto {

  @Enumerated(value = EnumType.STRING)
  private StateType pull;

  @Enumerated(value = EnumType.STRING)
  private StateType build;

  @Enumerated(value = EnumType.STRING)
  private StateType run;
}
