package com.dokkaebi.entity.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BuildTool {

  @Id @Column(name = "build_tool_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String buildToolName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "setting_config_id")
  private SettingConfig settingConfig;
}
