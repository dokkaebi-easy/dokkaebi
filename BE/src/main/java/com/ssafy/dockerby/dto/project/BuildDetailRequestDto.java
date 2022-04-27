package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.project.enums.BuildType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildDetailRequestDto {

  @NotNull
  private Long id;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private BuildType name;

  @Override
  public String toString() {
    return "BuildDetailRequestDto{" +
      "buildStateId=" + id +
      ", buildType=" + name +
      '}';
  }

}
