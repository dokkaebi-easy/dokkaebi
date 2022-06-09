package com.dokkaebi.repository.git;

import com.dokkaebi.entity.git.GitlabAccessToken;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GitlabAccessTokenRepository extends CrudRepository<GitlabAccessToken, Long> {

  List<GitlabAccessToken> findAllByAccessTokenIsNotNull();
}
