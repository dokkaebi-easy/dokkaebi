package com.dokkaebi.repository.user;

import com.dokkaebi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPrincipal(String principal);

    Optional<Object> findOneByPrincipal(String principal);

    Optional<Object> findOneByName(String name);

}
