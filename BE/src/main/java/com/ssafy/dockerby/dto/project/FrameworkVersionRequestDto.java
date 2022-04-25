package com.ssafy.dockerby.dto.project;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkVersionRequestDto {

  @NotNull
  private Long frameworkTypeId;

}
