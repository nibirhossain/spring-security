package com.nibir.hossain.brewery.bootstrap;

import com.nibir.hossain.brewery.domain.security.Authority;
import com.nibir.hossain.brewery.domain.security.CustomUser;
import com.nibir.hossain.brewery.domain.security.Role;
import com.nibir.hossain.brewery.repositories.security.AuthorityRepository;
import com.nibir.hossain.brewery.repositories.security.RoleRepository;
import com.nibir.hossain.brewery.repositories.security.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * Created by Nibir Hossain on 19.12.20
 */

@Component
public class UserDataLoader implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataLoader.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if(authorityRepository.count() == 0) {
            // loadSecurityData();
        }
    }

    private void loadSecurityData() {
        // Beer authorities
        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        // Customer authorities
        Authority createCustomer = authorityRepository.save(Authority.builder().permission("customer.create").build());
        Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
        Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
        Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());

        // Brewery authorities
        Authority createBrewery = authorityRepository.save(Authority.builder().permission("brewery.create").build());
        Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
        Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
        Authority deleteBrewery = authorityRepository.save(Authority.builder().permission("brewery.delete").build());

        // Beer order authorities
        /** For admins */
        Authority createOrder = authorityRepository.save(Authority.builder().permission("order.create").build());
        Authority readOrder = authorityRepository.save(Authority.builder().permission("order.read").build());
        Authority updateOrder = authorityRepository.save(Authority.builder().permission("order.update").build());
        Authority deleteOrder = authorityRepository.save(Authority.builder().permission("order.delete").build());
        /** For customers */
        Authority createOrderByCustomer = authorityRepository.save(Authority.builder().permission("customer.order.create").build());
        Authority readOrderByCustomer = authorityRepository.save(Authority.builder().permission("customer.order.read").build());
        Authority updateOrderByCustomer = authorityRepository.save(Authority.builder().permission("customer.order.update").build());
        Authority deleteOrderByCustomer = authorityRepository.save(Authority.builder().permission("customer.order.delete").build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());


        adminRole.setAuthorities(new HashSet<>(Set.of(createBeer, readBeer, updateBeer, deleteBeer,
                createCustomer, readCustomer, updateCustomer, deleteCustomer,
                createBrewery, readBrewery, updateBrewery, deleteBrewery,
                createOrder, readOrder, updateOrder, deleteOrder)));

        customerRole.setAuthorities(new HashSet<>(Set.of(readBeer, readCustomer, readBrewery,
                createOrderByCustomer, readOrderByCustomer, updateOrderByCustomer, deleteOrderByCustomer)));
        userRole.setAuthorities(new HashSet<>(Set.of(readBeer)));

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        userRepository.save(CustomUser.builder()
                .username("nibir")
                .password(encoder.encode("hossain"))
                .role(adminRole)
                .build());

        userRepository.save(CustomUser.builder()
                .username("sajib")
                .password(encoder.encode("mohammad"))
                .role(userRole)
                .build());

        CustomUser user = userRepository.save(CustomUser.builder()
                .username("salma")
                .password(encoder.encode("akther"))
                .role(customerRole)
                .build());

        LOGGER.info("Users loaded: " + userRepository.count());

        // user.getAuthorities().forEach(authority -> System.out.println(authority.getAuthority()));
        // Alternative
        user.getRoles().forEach(role -> {
            role.getAuthorities().forEach(authority -> System.out.println(authority.getPermission()));
        });
    }
}
