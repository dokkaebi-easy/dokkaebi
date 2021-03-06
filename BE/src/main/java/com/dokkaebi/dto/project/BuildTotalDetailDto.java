package com.dokkaebi.dto.project;

import com.dokkaebi.entity.project.enums.BuildType;
import com.dokkaebi.entity.project.enums.StateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildTotalDetailDto implements Comparable {

  @NotNull
  private Long buildStateId;

  @Nullable
  private Long buildNumber;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private BuildType buildType;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  private StateType stateType;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime registDate;

  @Nullable
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime lastModifiedDate;

  public Long getBuildStateIdAsLong(){
    return this.buildStateId;
  }

  @Override
  public int compareTo(Object o) {
    BuildTotalDetailDto e = (BuildTotalDetailDto) o;
    return getBuildStateIdAsLong().compareTo(e.getBuildStateIdAsLong());
  }

}
