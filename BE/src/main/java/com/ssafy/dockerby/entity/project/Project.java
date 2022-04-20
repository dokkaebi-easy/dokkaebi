package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.dto.project.ProjectRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Project {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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
      .id(projectRequestDto.getId())
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
