package com.nibir.hossain.brewery.repositories.security;

import com.nibir.hossain.brewery.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Nibir Hossain on 19.12.20
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
