package com.nibir.hossain.brewery.repositories.security;

/*
 * Created by Nibir Hossain on 19.12.20
 */

import com.nibir.hossain.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
