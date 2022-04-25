package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.project.states.Build;
import com.ssafy.dockerby.entity.project.states.Run;
import com.ssafy.dockerby.entity.project.states.Pull;
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
  private Pull pull;

  @OneToOne(mappedBy = "projectState" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Build build;

  @OneToOne(mappedBy = "projectState" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Run run;
  @Override
  public String toString() {
    return "ProjectState{" +
      "id=" + id +
      ", project=" + project +
      ", pull=" + pull +
      ", build=" + build +
      ", run=" + run +
      '}';
  }
}
