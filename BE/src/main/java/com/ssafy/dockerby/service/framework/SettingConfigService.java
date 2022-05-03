package com.ssafy.dockerby.service.framework;

import com.ssafy.dockerby.dto.project.framework.DbTypeResponseDto;
import com.ssafy.dockerby.dto.project.framework.DbVersionResponseDto;
import com.ssafy.dockerby.dto.project.framework.FrameworkTypeResponseDto;
import com.ssafy.dockerby.dto.project.framework.FrameworkVersionResponseDto;
import java.io.IOException;
import java.util.List;
import javassist.NotFoundException;

public interface SettingConfigService {

  List<FrameworkTypeResponseDto> frameworkTypes();

  FrameworkVersionResponseDto frameworkVersion(Long typeId) throws NotFoundException;

  List<DbTypeResponseDto> dbTypes();

  DbVersionResponseDto dbVersion(Long typeId) throws NotFoundException, IOException;

}
