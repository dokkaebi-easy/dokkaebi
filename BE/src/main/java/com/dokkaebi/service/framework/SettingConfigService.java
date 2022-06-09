package com.dokkaebi.service.framework;

import com.dokkaebi.dto.project.framework.DbTypeResponseDto;
import com.dokkaebi.dto.project.framework.DbVersionResponseDto;
import com.dokkaebi.dto.project.framework.FrameworkTypeResponseDto;
import com.dokkaebi.dto.project.framework.FrameworkVersionResponseDto;
import java.io.IOException;
import java.util.List;
import javassist.NotFoundException;

public interface SettingConfigService {

  List<FrameworkTypeResponseDto> frameworkTypes();

  FrameworkVersionResponseDto frameworkVersion(Long typeId) throws NotFoundException;

  List<DbTypeResponseDto> dbTypes();

  DbVersionResponseDto dbVersion(Long typeId) throws NotFoundException, IOException;

}
