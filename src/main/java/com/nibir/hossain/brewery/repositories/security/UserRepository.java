package com.nibir.hossain.brewery.repositories.security;

import com.nibir.hossain.brewery.domain.security.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Created by Nibir Hossain on 19.12.20
 */
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByUsername(String username);
}
