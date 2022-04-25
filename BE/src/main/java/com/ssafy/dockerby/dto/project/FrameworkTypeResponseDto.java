package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkTypeResponseDto {

  @NotNull
  private Long frameworkTypeId;

  @NotNull
  private String frameworkName;

}
