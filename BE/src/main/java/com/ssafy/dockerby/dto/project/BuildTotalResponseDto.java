package com.ssafy.dockerby.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  @NotNull
  @Builder.Default
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime registDate = LocalDateTime.now();

  @NotNull
  private StateDto state;

}
