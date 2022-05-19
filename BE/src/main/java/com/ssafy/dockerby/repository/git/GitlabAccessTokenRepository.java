package com.ssafy.dockerby.repository.git;

import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GitlabAccessTokenRepository extends CrudRepository<GitlabAccessToken, Long> {

  List<GitlabAccessToken> findAllByAccessTokenIsNotNull();
}
