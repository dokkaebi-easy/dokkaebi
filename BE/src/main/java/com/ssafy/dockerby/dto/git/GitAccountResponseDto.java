package com.ssafy.dockerby.dto.git;

import com.ssafy.dockerby.entity.git.GitlabAccount;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitAccountResponseDto {
  Long id;
  String name;  // fe 편의를 위해 name으로 지정

  public static GitAccountResponseDto of(Long id, String email) {
    return new GitAccountResponseDto(id, email);
  }

  public static GitAccountResponseDto from(GitlabAccount value) {
    return new GitAccountResponseDto(value.getId(), value.getEmail());
  }
}
