package com.github.jonathan5c.login.repository;

import com.github.jonathan5c.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserDetails> findUserByName(String name);
    boolean existsByEmail(String email);
}