package com.ssafy.dockerby.entity.project.enums.frameworks;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FrameworkType {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "framework_type_id")
  private Long id;

  private String frameworkName;

  @OneToMany(mappedBy = "frameworkType",cascade = CascadeType.ALL)
  @Builder.Default
  private List<Framework> frameworks = new ArrayList<>();
}
