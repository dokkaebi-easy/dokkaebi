package com.dokkaebi.dto.git;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitTokenRequestDto {

  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String accessToken;

}
