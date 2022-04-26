package com.ssafy.dockerby.entity.project.states;


import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.project.BuildState;
import com.ssafy.dockerby.entity.project.enums.StateType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Pull  {

  @Id
  @Column(name = "pull_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Builder.Default
  private StateType stateType = StateType.Waiting;

  @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  @JoinColumn(name = "build_state_id")
  private BuildState buildState;

  public static Pull from() {
    return Pull.builder().build();
  }

  public void updateBuildState(BuildState buildState){
    this.buildState = buildState;
  }

  public void updateStateType (String stateType){
    this.stateType= StateType.valueOf(stateType);
  }

}
