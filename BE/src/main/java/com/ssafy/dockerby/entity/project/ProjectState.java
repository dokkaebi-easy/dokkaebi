package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.project.states.DockerBuild;
import com.ssafy.dockerby.entity.project.states.DockerRun;
import com.ssafy.dockerby.entity.project.states.GitPull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectState extends BaseEntity {

  @Id
  @Column(name = "project_state_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
  @JoinColumn(name = "project_id")
  private Project project;

  @OneToOne(mappedBy = "projectState" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private GitPull gitPull;

  @OneToOne(mappedBy = "projectState" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private DockerBuild dockerBuild;

  @OneToOne(mappedBy = "projectState" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private DockerRun dockerRun;

}
