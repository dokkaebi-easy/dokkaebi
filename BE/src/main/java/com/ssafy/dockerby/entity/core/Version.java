package com.ssafy.dockerby.entity.core;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Version {

  @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "version_id")
  private Long id;

  private String inputVersion;
  private String dockerVersion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="language_id")
  private Language language;
}
