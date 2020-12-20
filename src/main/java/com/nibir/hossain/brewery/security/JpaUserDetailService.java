package com.nibir.hossain.brewery.security;

/*
 * Created by Nibir Hossain on 19.12.20
 */

import com.nibir.hossain.brewery.domain.security.CustomUser;
import com.nibir.hossain.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDetailService.class);
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Getting user details using JPA");
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));

    }
}
