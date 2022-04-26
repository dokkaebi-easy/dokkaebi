package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.entity.project.states.Build;
import com.ssafy.dockerby.entity.project.states.Pull;
import com.ssafy.dockerby.entity.project.states.Run;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BuildState {

  @Id
  @Column(name = "build_state_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer buildNumber;

  @CreatedDate
  private LocalDateTime registDate;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "project_id")
  private Project project;

  @OneToOne(mappedBy = "buildState", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Pull pull;

  @OneToOne(mappedBy = "buildState", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Build build;

  @OneToOne(mappedBy = "buildState", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Run run;


  public static BuildState from() {
    return BuildState.builder().build();
  }

  public void setBuildNumber(int number){
    this.buildNumber=number;
  }


  public void setState(Pull pull, Build build, Run run) {
    this.pull=pull;
    this.build=build;
    this.run=run;
  }

  public void setProject(Project project) {
    this.project = project;
    project.addBuildState(this);
  }

  @Override
  public String toString() {
    return "BuildState{" +
        "id=" + id +
        ", project=" + project +
        ", pull=" + pull +
        ", build=" + build +
        ", run=" + run +
        '}';
  }
}
