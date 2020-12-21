package com.nibir.hossain.brewery.security;

/*
 * Created by Nibir Hossain on 20.12.20
 */

import com.nibir.hossain.brewery.domain.security.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BeerOrderAuthenticationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeerOrderAuthenticationManager.class);

    public boolean customerIdMatches(Authentication authentication, UUID customerId) {
        CustomUser authenticatedUser = (CustomUser) authentication.getPrincipal();
        LOGGER.info("Authenticated customer user ID " + authenticatedUser.getCustomer().getId() + " customer ID " + customerId);

        return authenticatedUser.getCustomer().getId().equals(customerId);
    }
}
