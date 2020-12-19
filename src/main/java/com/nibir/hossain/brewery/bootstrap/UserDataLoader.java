package com.nibir.hossain.brewery.bootstrap;

import com.nibir.hossain.brewery.domain.security.Authority;
import com.nibir.hossain.brewery.domain.security.CustomUser;
import com.nibir.hossain.brewery.repositories.security.AuthorityRepository;
import com.nibir.hossain.brewery.repositories.security.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * Created by Nibir Hossain on 19.12.20
 */

@Component
public class UserDataLoader implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataLoader.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if(authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }

    private void loadSecurityData() {
        Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority customerRole = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());
        Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());

        userRepository.save(CustomUser.builder()
                .username("nibir")
                .password(encoder.encode("hossain"))
                .authority(adminRole)
                .build());

        userRepository.save(CustomUser.builder()
                .username("sajib")
                .password(encoder.encode("mohammad"))
                .authority(userRole)
                .build());

        userRepository.save(CustomUser.builder()
                .username("salma")
                .password(encoder.encode("akther"))
                .authority(customerRole)
                .build());

        LOGGER.info("Users loaded: " + userRepository.count());
    }
}
