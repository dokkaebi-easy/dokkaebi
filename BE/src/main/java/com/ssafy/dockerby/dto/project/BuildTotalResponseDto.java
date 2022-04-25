package com.ssafy.dockerby.dto.project;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildTotalResponseDto {

  private Long projectStateId;

  private StateDto state;

}
