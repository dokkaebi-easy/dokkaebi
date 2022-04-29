package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.dto.project.ProjectConfigDto;
import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.ConfigHistory;
import com.ssafy.dockerby.entity.git.GitlabConfig;
import com.ssafy.dockerby.entity.project.enums.StateType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Project extends BaseEntity {

  @Id
  @Column(name = "project_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 60 , unique = true)
  private String projectName;

  @Enumerated(value = EnumType.STRING)
  @Builder.Default
  private StateType stateType = StateType.valueOf("Waiting");

//연관관계 매핑
  @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
  @Builder.Default
  private List<ProjectConfig> projectConfigs = new ArrayList<>();

  @OneToOne(mappedBy = "project", fetch = FetchType.LAZY)
  private GitlabConfig gitConfig;

  // History 매핑
  @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
  @Builder.Default
  private List<ConfigHistory> histories = new ArrayList<>();

  @OneToMany(mappedBy = "project" , cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
  @Builder.Default
  private List<BuildState> buildStates = new ArrayList<>();


//비지니스 로직
  public static Project from(ProjectConfigDto projectConfigDto) {
    return Project.builder()
      .projectName(projectConfigDto.getProjectName())
      .build();
  }

  public Project updateState(StateType state){
    this.stateType = state;
    return this;
  }

  public void addProjectConfigs(List<ProjectConfig> configs) {
    this.projectConfigs = configs;
    configs.forEach(config -> config.setProject(this));
  }

  public void addProjectConfig(ProjectConfig config) {
    this.projectConfigs.add(config);
    config.setProject(this);
  }

  public void addBuildState(BuildState buildState){
    this.buildStates.add(buildState);
  }

  public void setConfig(GitlabConfig config) {
    this.gitConfig = config;
  }
}
