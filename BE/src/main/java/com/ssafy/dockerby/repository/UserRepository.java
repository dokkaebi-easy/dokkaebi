package com.ssafy.dockerby.repository;

import com.ssafy.dockerby.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<String> findByPrincipal(String principal);

}
