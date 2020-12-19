package com.nibir.hossain.brewery.repositories.security;

/*
 * Created by Nibir Hossain on 19.12.20
 */

import com.nibir.hossain.brewery.domain.security.Authority;
import com.nibir.hossain.brewery.domain.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDetailService.class);
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Getting user details using JPA");
        CustomUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialNonExpired(),
                user.getAccountNonLocked(),
                convertToSpringAuthorities(user.getAuthorities())
        );
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        if(authorities == null || authorities.size() <= 0) {
            return new HashSet<>();
        }

        return authorities.stream()
                .map(Authority::getRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
