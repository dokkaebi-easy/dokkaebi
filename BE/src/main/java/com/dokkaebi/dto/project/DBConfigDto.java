package com.dokkaebi.dto.project;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DBConfigDto {

  @Positive
  private Long frameworkId;

  @NotBlank
  private String name;

  @NotBlank
  private String version;

  @NotBlank
  private String port;

  private String dumpLocation;

  private List<ConfigProperty> properties;

}
