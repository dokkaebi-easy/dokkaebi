//package com.ssafy.dockerby.project;
//
//import com.ssafy.dockerby.common.exception.UserDefindedException;
//import com.ssafy.dockerby.dto.project.ProjectRequestDto;
//import com.ssafy.dockerby.dto.project.ProjectResponseDto;
//import com.ssafy.dockerby.repository.project.ProjectRepository;
//import com.ssafy.dockerby.service.project.ProjectService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//
//@ExtendWith(MockitoExtension.class)
//public class ProjectTest {
//
//  @Mock
//  private ProjectRepository projectRepository;
//  @InjectMocks
//  private ProjectService projectService;
//  ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
//      .projectName("dockerby")
//      .description("손쉬운 배포 프로젝트")
//      .settingJson("{project : 123asd , projectUrl : http://localhost:8080 }")
//      .build();
//
//  @Test
//  public void 프로젝트_생성_state_변경_SuccessTest() throws UserDefindedException, IOException {
//    //requestDto 생성
//    ProjectResponseDto testDto = projectService.createProject(projectRequestDto);
//    Assertions.assertEquals(testDto.getState(),"Progressing");
//  }
//
//}
