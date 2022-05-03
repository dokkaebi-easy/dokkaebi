package com.ssafy.dockerby.entity.core;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SettingConfig {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "setting_config_id")
  private Long id;

  private String settingConfigName;

  private String groupCode;

  private String option;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id")
  private Language language;

  @OneToMany(mappedBy = "settingConfig")
  private List<BuildTool> buildTools = new ArrayList<>();
}
