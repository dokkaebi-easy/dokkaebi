package com.ssafy.dockerby.dto.project;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildTotalResponseDto {

  private Long buildStateId;

  private StateDto state;

}
