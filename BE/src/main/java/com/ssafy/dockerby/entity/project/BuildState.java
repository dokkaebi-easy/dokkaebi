package com.ssafy.dockerby.entity.project;

import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.git.WebhookHistory;
import com.ssafy.dockerby.entity.project.enums.BuildType;
import com.ssafy.dockerby.entity.project.enums.StateType;
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
public class BuildState extends BaseEntity {

  @Id
  @Column(name = "build_state_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long buildNumber;

  @Enumerated(value = EnumType.STRING)
  private BuildType buildType;

  @Enumerated(value = EnumType.STRING)
  private StateType stateType;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "project_id")
  private Project project;

  @OneToOne(mappedBy = "buildState", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private WebhookHistory webhookHistory;

  public static BuildState from() {
    return BuildState.builder().build();
  }

  public void updateStateType(String type) {
    this.stateType = StateType.valueOf(type);

  }

  public void setProject(Project project) {
    this.project = project;
    project.addBuildState(this);
  }

  public void setWebhookHistory(WebhookHistory history) {
    this.webhookHistory = history;
  }

  @Override
  public String toString() {
    return "BuildState{" +
      "id=" + id +
      ", buildNumber=" + buildNumber +
      ", buildType=" + buildType +
      ", stateType=" + stateType +
      ", project=" + project +
      ", webhookHistory=" + webhookHistory +
      '}';
  }
}
