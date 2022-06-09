package com.dokkaebi.repository.project;

import com.dokkaebi.entity.core.SettingConfig;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface SettingConfigRepository extends CrudRepository<SettingConfig,Long> {
  List<SettingConfig> findAllByGroupCode(String groupCode);

  Optional<SettingConfig> findBySettingConfigName(String settingConfigName);
}
