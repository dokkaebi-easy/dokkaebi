package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.project.enums.StateType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

  @Id
  @Column(name = "project_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 60 , unique = true)
  private String projectName;

  private String description;

  @Enumerated(value = EnumType.STRING)
  @Builder.Default
  private StateType stateType = StateType.valueOf("Processing");

  private String configLocation;

  //연관관계 매핑

  //User 매핑
//  @OneToMany(fetch = FetchType.LAZY)
//  @JoinColumn(name ="user_id")
//  private User user;

  // History 매핑
//  private List<String> history;
  @OneToMany(mappedBy = "project" , cascade = CascadeType.ALL)
  @Builder.Default
  private List<ProjectState> projectStates = new ArrayList<>();


//비지니스 로직
  public static Project of(ProjectRequestDto projectRequestDto,String configLocation) {

    return Project.builder()
      .projectName(projectRequestDto.getProjectName())
      .description(projectRequestDto.getDescription())
      .configLocation(configLocation)
      .build();
  }

  public Project updateState(String state){
    this.stateType = StateType.valueOf(state);
    return this;
  }
}
