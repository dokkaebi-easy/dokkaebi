package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

  @Id
  @Column(name = "project_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 60 , unique = true)
  private String projectName;

  private String description;

  @Builder.Default
  private String state = "fail";

  private String configLocation;

  //연관관계 매핑

  //User 매핑
//  @OneToMany(fetch = FetchType.LAZY)
//  @JoinColumn(name ="user_id")
//  private User user;

  // History 매핑
//  private List<String> history;

//비지니스 로직
  public static Project of(ProjectRequestDto projectRequestDto,String configLocation) {

    return Project.builder()
      .projectName(projectRequestDto.getProjectName())
      .description(projectRequestDto.getDescription())
      .configLocation(configLocation)
      .build();
  }

  public Project updateState(String state){
    this.state = state;
    return this;
  }
}
