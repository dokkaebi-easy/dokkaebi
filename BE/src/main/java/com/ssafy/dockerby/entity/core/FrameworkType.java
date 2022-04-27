package com.ssafy.dockerby.entity.core;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FrameworkType {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "framework_type_id")
  private Long id;

  private String frameworkName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id")
  private Language language;

  @OneToMany(mappedBy = "frameworkType")
  private List<BuildTool> buildTools = new ArrayList<>();
}
