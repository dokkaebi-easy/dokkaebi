package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildTotalResponseDto {

  @NotNull
  private Long buildStateId;

  @NotNull
  private Long buildNumber;

  @Builder.Default
  private LocalDateTime registDate = LocalDateTime.now();

  @NotNull
  private StateDto state;

}
