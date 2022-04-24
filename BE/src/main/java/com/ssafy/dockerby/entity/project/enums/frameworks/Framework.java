package com.ssafy.dockerby.entity.project.enums.frameworks;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Framework {

  @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "framework_id")
  private Long id;

  private String buildType;

  private String version;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="framework_type_id")
  private FrameworkType frameworkType;
}
