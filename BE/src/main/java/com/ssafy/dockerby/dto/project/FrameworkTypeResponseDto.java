package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkTypeResponseDto {

  @NotNull
  private Long frameworkId;

  @NotNull
  private String frameworkName;

}
