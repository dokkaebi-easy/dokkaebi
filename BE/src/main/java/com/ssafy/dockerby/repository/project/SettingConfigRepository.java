package com.ssafy.dockerby.repository.project;

import com.ssafy.dockerby.entity.core.SettingConfig;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface SettingConfigRepository extends CrudRepository<SettingConfig,Long> {
  List<SettingConfig> findAllByGroupCode(String groupCode);

  Optional<SettingConfig> findBySettingConfigName(String settingConfigName);
}
