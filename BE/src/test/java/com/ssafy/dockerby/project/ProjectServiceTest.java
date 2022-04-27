//package com.ssafy.dockerby.project;
//
//import com.ssafy.dockerby.common.exception.UserDefindedException;
//import com.ssafy.dockerby.dto.project.ProjectRequestDto;
//import com.ssafy.dockerby.dto.project.ProjectResponseDto;
//import com.ssafy.dockerby.entity.project.ProjectState;
//import com.ssafy.dockerby.repository.project.ProjectRepository;
//import com.ssafy.dockerby.service.project.ProjectServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.crossstore.ChangeSetPersister;
//
//import java.io.IOException;
//
//@ExtendWith(MockitoExtension.class)
//public class ProjectServiceTest {
//
//  @Mock
//  private ProjectRepository projectRepository;
//  @InjectMocks
//  private ProjectServiceImpl projectService;
//  ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
//      .projectName("dockerby")
//      .description("손쉬운 배포 프로젝트")
//      .settingJson("{project : 123asd , projectUrl : http://localhost:8080 }")
//      .build();
//
////TODO / TEST : 테스트 쪽에서 디비 쓰는방법을 알아보자!!
//
//  @Test
//  public void 프로젝트_생성_success_test() throws UserDefindedException, IOException {
//    //requestDto 생성
//    ProjectResponseDto testDto = projectService.createProject(projectRequestDto);
//    Assertions.assertEquals(testDto.getState(),"Processing");
//  }
//
//  @Test
//  public void 프로젝트_타입별_상태변경_test() throws UserDefindedException, IOException, ChangeSetPersister.NotFoundException {
//
//    projectService.createProject(projectRequestDto);
//    ProjectState projectState = projectService.build(projectRequestDto);
//    Assertions.assertEquals(projectState.getProject().getProjectName(),"dockerby");
//    Assertions.assertEquals(projectState.getPull().getStateType(),"Done");
//    Assertions.assertEquals(projectState.getBuild().getStateType(),"Done");
//    Assertions.assertEquals(projectState.getRun().getStateType(),"Done");
//  }
//}
