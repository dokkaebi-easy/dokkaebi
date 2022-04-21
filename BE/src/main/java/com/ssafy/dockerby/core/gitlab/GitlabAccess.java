package com.ssafy.dockerby.core.gitlab;

import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;


@Slf4j
public class GitlabAccess {

    public static GitLabApi gitLabApi;

    // git repository url 검증
    public static Boolean isGitlabRepositoryUrl(Object projectIdOrPath, String repositoryUrl) {
        try {
            // Get a specific project, which is owned by the authentication user.
            String gitlabHttpUrl = gitLabApi.getProjectApi().getProject(projectIdOrPath).getHttpUrlToRepo();
            if (gitlabHttpUrl.equals(repositoryUrl)) {
                return true;
            }
            return false;
        }catch (GitLabApiException e) {
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            return false;
        }
    }




}
