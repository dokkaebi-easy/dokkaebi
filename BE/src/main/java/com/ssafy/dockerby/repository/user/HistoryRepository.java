package com.ssafy.dockerby.repository.User;

import com.ssafy.dockerby.entity.ConfigHistory;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<ConfigHistory, Long> {

    Optional<ConfigHistory> findAllByProjectId(Long aLong);


}
