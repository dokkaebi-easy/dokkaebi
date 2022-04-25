package com.ssafy.dockerby.entity.project.frameworks;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Language {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "language_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "language")
  private List<Version> versions = new ArrayList<>();


}
