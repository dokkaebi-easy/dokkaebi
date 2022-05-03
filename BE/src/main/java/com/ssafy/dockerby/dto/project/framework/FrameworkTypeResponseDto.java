package com.ssafy.dockerby.dto.project.framework;

import com.ssafy.dockerby.entity.core.SettingConfig;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameworkTypeResponseDto {

  @NotNull
  private Long id;

  @NotNull
  private String name;

  public static FrameworkTypeResponseDto from(SettingConfig settingConfig) {
    return new FrameworkTypeResponseDto(settingConfig.getId(), settingConfig.getSettingConfigName());
  }

}
