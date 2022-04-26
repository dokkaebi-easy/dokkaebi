package com.ssafy.dockerby.dto.git;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitAccountRequestDto {

  private Long id;

  @NotNull
  private String email;

  @NotNull
  private String password;

  private String username;

}
