package com.dokkaebi.service.framework;

import com.dokkaebi.repository.project.SettingConfigRepository;
import com.dokkaebi.dto.framework.DbPropertyConfigDto;
import com.dokkaebi.dto.project.framework.DbTypeResponseDto;
import com.dokkaebi.dto.project.framework.DbVersionResponseDto;
import com.dokkaebi.dto.project.framework.FrameworkTypeResponseDto;
import com.dokkaebi.dto.project.framework.FrameworkVersionResponseDto;
import com.dokkaebi.entity.core.SettingConfig;
import com.dokkaebi.util.FileManager;
import com.dokkaebi.util.PathParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingConfigServiceImpl implements
    SettingConfigService {

  private final SettingConfigRepository configRepository;

  private final PathParser pathParser;

  @Override
  public List<FrameworkTypeResponseDto> frameworkTypes() {
    List<SettingConfig> frameworkTypes = configRepository.findAllByGroupCode("Framework");

    List<FrameworkTypeResponseDto> result = new ArrayList<>();
    frameworkTypes.forEach(framework -> result.add(FrameworkTypeResponseDto.from(framework)));

    return result;
  }

  @Override
  public FrameworkVersionResponseDto frameworkVersion(Long typeId) throws NotFoundException {
    log.info("frameworkVersion.typeId {}", typeId);

    SettingConfig settingConfig = configRepository.findById(typeId)
        .orElseThrow(() -> new NotFoundException("frameworkVersion not found : " + typeId));

    List<String> versions = new ArrayList<>();
    List<String> buildTools = new ArrayList<>();

    settingConfig.getLanguage().getVersions()
        .forEach(version -> versions.add(version.getInputVersion()));
    settingConfig.getBuildTools()
        .forEach(buildTool -> buildTools.add(buildTool.getBuildToolName()));

    FrameworkVersionResponseDto result = FrameworkVersionResponseDto.from(
        settingConfig.getLanguage().getLanguageName(), versions, buildTools);
    log.info("frameworkVersion.response {}", result);

    return result;
  }

  @Override
  public List<DbTypeResponseDto> dbTypes() {
    List<SettingConfig> dbTypes = configRepository.findAllByGroupCode("Dbms");

    List<DbTypeResponseDto> result = new ArrayList<>();
    dbTypes.forEach(dbType -> result.add(new DbTypeResponseDto(dbType.getId(),
        dbType.getSettingConfigName())));

    return result;
  }

  @Override
  public DbVersionResponseDto dbVersion(Long typeId) throws NotFoundException, IOException {

    log.info("getDbVersion.typeId {}", typeId);

    SettingConfig settingConfig = configRepository.findById(typeId)
        .orElseThrow(() -> new NotFoundException("getDbVersion not found : " + typeId));

    List<String> versions = new ArrayList<>();
    List<String> properties = new ArrayList<>();

    settingConfig.getLanguage().getVersions()
        .forEach(version -> versions.add(version.getInputVersion()));

    if (!settingConfig.getOption().isBlank()) {
      String filePath = pathParser.dokkaebiConfigPath().toString();
      if(new File(filePath).exists()) {
        DbPropertyConfigDto dbPropertyConfigDto = FileManager.loadJsonFile(filePath,
            settingConfig.getOption(), DbPropertyConfigDto.class);
        dbPropertyConfigDto.getProperties().forEach(property -> properties.add(property));
      }
    }

    DbVersionResponseDto result = new DbVersionResponseDto(versions,properties);
    log.info("frameworkVersion.response {}", result);

    return result;
  }
}
