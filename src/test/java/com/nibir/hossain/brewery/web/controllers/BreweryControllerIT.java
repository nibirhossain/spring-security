package com.nibir.hossain.brewery.web.controllers;

/*
 * Created by Nibir Hossain on 19.12.20
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BreweryControllerIT extends BaseIT {
    @Test
    void listBreweriesWithCustomerRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("salma", "akther")))
                .andExpect(status().isOk());
    }

    @Test
    void listBreweriesWithUserRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweriesWithAdminRole() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("nibir", "hossain")))
                .andExpect(status().is2xxSuccessful());
    }
    
    @Test
    void listBreweriesWithNoAuthUser() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listBreweriesJsonWithCustomerRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("salma", "akther")))
                .andExpect(status().isOk());
    }

    @Test
    void listBreweriesJsonWithUserRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweriesJsonWithAdminRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("nibir", "hossain")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweriesJsonWithNoAuthUser() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());
    }
}
