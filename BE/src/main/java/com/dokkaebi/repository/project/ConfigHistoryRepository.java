package com.dokkaebi.repository.project;

import com.dokkaebi.entity.ConfigHistory;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigHistoryRepository extends JpaRepository<ConfigHistory, Long> {

    Optional<ConfigHistory> findAllByProjectId(Long aLong);


}
