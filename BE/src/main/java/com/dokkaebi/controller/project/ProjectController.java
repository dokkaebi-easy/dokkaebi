package com.dokkaebi.controller.project;


import com.dokkaebi.core.gitlab.GitlabWrapper;
import com.dokkaebi.core.gitlab.dto.GitlabWebHookDto;
import com.dokkaebi.dto.project.BuildDetailResponseDto;
import com.dokkaebi.dto.project.BuildTotalResponseDto;
import com.dokkaebi.dto.project.ConfigHistoryListResponseDto;
import com.dokkaebi.dto.project.ProjectConfigDto;
import com.dokkaebi.dto.project.ProjectListResponseDto;
import com.dokkaebi.dto.project.framework.DbTypeResponseDto;
import com.dokkaebi.dto.project.framework.DbVersionResponseDto;
import com.dokkaebi.dto.project.framework.FrameworkTypeResponseDto;
import com.dokkaebi.dto.project.framework.FrameworkVersionResponseDto;
import com.dokkaebi.entity.project.Project;
import com.dokkaebi.service.framework.SettingConfigService;
import com.dokkaebi.service.project.ProjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javassist.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Project"})
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
  private final ProjectServiceImpl projectService;

  private final SettingConfigService configService;

  @ApiOperation(value = "???????????? ??????")
  @DeleteMapping("/{projectId}")
  public ResponseEntity deleteProject(@PathVariable Long projectId)
      throws NotFoundException, IOException {

    projectService.deleteContainer(projectId);
    projectService.deleteProject(projectId);



    Map<String, Object> map = new HashMap<>();
    map.put("status", "Success");
    return ResponseEntity.ok(map);
  }

  @ApiOperation(value = "???????????? ??????", notes = "?????? ?????? ??????????????? ????????????.")
  @PutMapping("/stop/{projectId}")
  public ResponseEntity stopProject(@PathVariable Long projectId)
      throws NotFoundException, IOException {

    projectService.stopContainer(projectId);

    Map<String, Object> map = new HashMap<>();
    map.put("status", "Success");
    return ResponseEntity.ok(map);
  }

  @ApiOperation(value = "???????????? ??????", notes = "??????????????? ????????????.")
  @PostMapping
  public ResponseEntity upsertProject(HttpServletRequest request,@Valid @RequestBody ProjectConfigDto projectConfigDto) throws NotFoundException, IOException {
    //?????? ????????????
    log.info("API Request received : projectConfigDto = {} ",projectConfigDto.toString());

    Map<Project, String> upsertResult = projectService.upsert(projectConfigDto);

    //???????????? ??????
    try { //????????? ?????????
      for(Project project : upsertResult.keySet())
        projectService.createConfigHistory(request,project,upsertResult.get(project));
    }catch (Exception e){ // ????????? ????????? //ex)git hook ??????
      log.error("information does not exist Exception {}",e);
    }

    Map<String, Object> map = new HashMap<>();
    map.put("status", "Success");

    log.info("done : {}", projectConfigDto);
    return ResponseEntity.ok(map);
  }

  @ApiOperation(value = "???????????? ?????? ???", notes = "???????????? ?????? ????????? ?????? ??????.")
  @GetMapping("/config/{projectId}")
  public ResponseEntity projectConfig(@PathVariable Long projectId)
      throws NotFoundException, IOException {
    log.info("API Request received : projectId = {} ",projectId);
    return ResponseEntity.ok(projectService.findConfigById(projectId));
  }

  @ApiOperation(value = "???????????? ??????", notes = "???????????? ????????? ??????.")
  @PostMapping("/build")
  public ResponseEntity buildProject(Long projectId ) throws IOException, NotFoundException {
    log.info("API Request received : projectId = {} ",projectId);
    LocalDateTime startTime = LocalDateTime.now();

    //???????????? ?????? ?????? ??????
    projectService.build(projectId, null);
    //build ??????
    projectService.pullStart(projectId, null);
    projectService.buildStart(projectId, null);
    projectService.runStart(projectId, null);

    LocalDateTime endTime = LocalDateTime.now();
    String duration = projectService.makeDuration(startTime,endTime);

    return ResponseEntity.ok(projectService.updateProjectDone(projectId,duration));
  }

  @ApiOperation(value = "????????? ?????? ??????", notes = "????????? ?????? ????????? ?????? ?????????.")
  @GetMapping("/frameworkType")
  public ResponseEntity<List<FrameworkTypeResponseDto>> getFrameworkType(){
    log.info("API Request received");

    //type list ??????
    List<FrameworkTypeResponseDto> frameworkTypes = configService.frameworkTypes();

    //type list ??????
    log.info("API Response return");
    return ResponseEntity.ok(frameworkTypes);
  }
  @ApiOperation(value = "????????? ?????? ??????", notes = "????????? ?????? ????????? ????????? ?????? ?????????.")
  @GetMapping("/frameworkVersion")
  public ResponseEntity<FrameworkVersionResponseDto> GetFrameworkVersion(@RequestParam Long typeId) throws NotFoundException {
    //version ?????? ?????? ??????
    log.info("frameworkVersion API received typeId: {}",typeId);

    return ResponseEntity.ok(configService.frameworkVersion(typeId));
  }

  @ApiOperation(value = "?????????????????? ??????", notes = "?????????????????? ?????? ????????? ???????????????.")
  @GetMapping("/dbType")
  public ResponseEntity<List<DbTypeResponseDto>> dbTypes(){
    //type ?????? ?????? ??????
    log.info("dbType API received");

    //type list ??????
    List<DbTypeResponseDto> dbTypes = configService.dbTypes();

    //type list ??????
    return ResponseEntity.ok(dbTypes);
  }

  @ApiOperation(value = "?????????????????? ?????? ??????", notes = "?????????????????? ?????? ????????? ?????? ????????? ???????????????.")
  @GetMapping("/dbVersion")
  public ResponseEntity<DbVersionResponseDto> dbVersions(@RequestParam Long typeId)
      throws NotFoundException, IOException {
    //version ?????? ?????? ??????
    log.info("API Request received : typeId = {}",typeId);

    log.info("API Response return");
    return ResponseEntity.ok(configService.dbVersion(typeId));
  }


  @ApiOperation(value = "???????????? ?????? ?????? ??????", notes = "???????????? ?????? ?????? ????????? ????????????.")
  @GetMapping("/build/total")
  public ResponseEntity<List<BuildTotalResponseDto>> buildTotal(Long projectId) throws NotFoundException {
    log.info("API Request received : projectId = {}",projectId);
    // ?????? ?????? ??????
    log.info("buildTotal API request received {} ", projectId);

    List<BuildTotalResponseDto> buildTotalResponseDtos = projectService.buildTotal(projectId);

    log.info("API Response return");
    return ResponseEntity.ok(buildTotalResponseDtos);
  }

  @ApiOperation(value = "???????????? ??????", notes = "???????????? ?????? ????????? ????????????.")
  @GetMapping("/build/detail")
  public ResponseEntity<BuildDetailResponseDto> buildDetail(Long buildStateId) throws NotFoundException {
    log.info("API Request received : buildStateId = {}",buildStateId);
    //?????? ?????? ??????
    log.info("buildDetail API request received {}",buildStateId);

    //???????????? state ?????? stateResponse ??????
    BuildDetailResponseDto buildDetailResponseDto = projectService.buildDetail(buildStateId);

    log.info("API Response return");
    return ResponseEntity.ok(buildDetailResponseDto);
  }

  @ApiOperation(value = "???????????? ??????", notes = "???????????? ????????? ????????????.")
  @GetMapping("/all")
  public ResponseEntity<List<ProjectListResponseDto>> projects() throws IOException {
    log.info("API Request received");

    List<ProjectListResponseDto> projectList = projectService.projectList();

    log.info("API Response return");
    return ResponseEntity.ok(projectList);
  }


  @ApiOperation(value = "ConfigHistory ?????????", notes = "ConfigHistory ????????? ????????????.")
  @GetMapping("/configHistory")
  public ResponseEntity<List<ConfigHistoryListResponseDto>> configHistory() {
    log.info("API Request received");


    List<ConfigHistoryListResponseDto> configHistoryList = projectService.historyList();

    log.info("API Response return");
    return ResponseEntity.ok(configHistoryList);
  }

  @PostMapping("/hook/{projectName}")
  public ResponseEntity webHook(@PathVariable String projectName,
      @RequestHeader(name = "X-Gitlab-Token") String token,
      @RequestBody Map<String, Object> params) throws NotFoundException, IOException {
    log.info("API Request received : projectName = {}",projectName);
    LocalDateTime startTime=LocalDateTime.now();
    GitlabWebHookDto webHookDto = GitlabWrapper.wrap(params);

    Project project = projectService.findProjectByName(projectName)
        .orElseThrow(() -> new NotFoundException("Webhook projectName : "+projectName));

    if(!token.equals(project.getGitConfig().getSecretToken()))
      throw new IllegalArgumentException("Unauthorized secret token "+token);

    log.debug("ProjectController.Webhook : X-Gitlab-Toke : {} / " , token,params);

    //???????????? ?????? ?????? ??????
    projectService.build(project.getId(), webHookDto);
    projectService.pullStart(project.getId(), webHookDto);
    projectService.buildStart(project.getId(), webHookDto);
    projectService.runStart(project.getId(), webHookDto);

    LocalDateTime endTime=LocalDateTime.now();

    String duration =projectService.makeDuration(startTime,endTime);
    log.info("API Response null");
    return ResponseEntity.ok(projectService.updateProjectDone(project.getId(),duration));
  }
}