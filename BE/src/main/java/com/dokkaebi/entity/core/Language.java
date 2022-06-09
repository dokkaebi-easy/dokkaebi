package com.dokkaebi.entity.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  private String languageName;

  @OneToMany(mappedBy = "language")
  private List<Version> versions = new ArrayList<>();

  public Optional<Version> findVersionByInput(String inputVersion) {
    for(Version version : versions) {
      if(version.getInputVersion().equals(inputVersion))
        return Optional.ofNullable(version);
    }
    return Optional.ofNullable(null);
  }

  public Optional<Version> findVersionByDocker(String dockerVersion) {
    for(Version version : versions) {
      if(version.getDockerVersion().equals(dockerVersion))
        return Optional.ofNullable(version);
    }
    return Optional.ofNullable(null);
  }

}
