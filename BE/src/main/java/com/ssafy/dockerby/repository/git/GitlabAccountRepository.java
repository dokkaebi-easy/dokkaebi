package com.ssafy.dockerby.repository.git;

import com.ssafy.dockerby.entity.git.GitlabAccount;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GitlabAccountRepository extends CrudRepository<GitlabAccount, Long> {

  List<GitlabAccount> findAllByEmailIsNotNull();
}
