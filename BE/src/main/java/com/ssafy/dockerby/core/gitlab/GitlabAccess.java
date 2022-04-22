package com.ssafy.dockerby.core.gitlab;

import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;


@Slf4j
public class GitlabAccess {

    public static GitLabApi gitLabApi;

    // git repository url 검증
    public static Boolean isGitlabRepositoryUrl(Object projectIdOrPath, String repositoryUrl) {
        // 파라메터를 Object 타입으로 받아온 이유는 gitlab lib로 요청하는 함수 gitLabApi.getProjectApi().getProject(projectIdOrPath) 에서
        // projectIdOrPath 값이 Long or String 값으로 받아서 사용하고 있기 때문에 그대로 차용했습니다.
        // 아무래도 gitLab lib에 요청해서 사용하다보니 대체로 해당 함수의 규칙을 따라가려고 그대로 사용하였습니다.
        // 현재 우리 코드에서는 projectIdOrPath : projectId 로 Long 타입만 사용하고 있습니다.

        try {
            // Get a specific project, which is owned by the authentication user.
            String gitlabHttpUrl = gitLabApi.getProjectApi().getProject(projectIdOrPath).getHttpUrlToRepo();
            if (gitlabHttpUrl.equals(repositoryUrl)) {
                return true;
            }
            return false;
        }catch (GitLabApiException e) {
            log.error(e.getMessage(), e);
            return true;
        }
    }


    // git repository branch 검증
    public static Boolean isGitlabBranch(Object projectIdOrPath, String branchName) {
        try {
            // Get a single project repository branch.
            gitLabApi.getRepositoryApi().getBranch(projectIdOrPath, branchName);
            return true;
        } catch (GitLabApiException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }




}
