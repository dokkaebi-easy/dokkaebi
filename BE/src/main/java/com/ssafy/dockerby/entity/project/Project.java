package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.dto.project.ProjectConfigDto;
import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.ConfigHistory;
import com.ssafy.dockerby.entity.git.GitlabConfig;
import com.ssafy.dockerby.entity.project.enums.StateType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Null;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.lang.Nullable;

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

  @Nullable
  private LocalDateTime lastSuccessDate;

  @Nullable
  private LocalDateTime lastFailDate;

  @Nullable

  private String lastDuration;


//연관관계 매핑

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
    if("Done".equals(state.toString()))this.lastSuccessDate = LocalDateTime.now();
    if("Failed".equals(state.toString()))this.lastFailDate = LocalDateTime.now();
    return this;
  }

  public void updateLastDuration(String duration){
    this.lastDuration=duration;
  }

  public void addBuildState(BuildState buildState){
    this.buildStates.add(buildState);
  }

  public void setConfig(GitlabConfig config) {
    this.gitConfig = config;
  }
}
