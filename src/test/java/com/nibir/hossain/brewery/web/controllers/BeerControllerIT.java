package com.nibir.hossain.brewery.web.controllers;

/*
 * Created by Nibir Hossain on 18.12.20
 */

import com.nibir.hossain.brewery.repositories.BeerInventoryRepository;
import com.nibir.hossain.brewery.repositories.BeerRepository;
import com.nibir.hossain.brewery.repositories.CustomerRepository;
import com.nibir.hossain.brewery.services.BeerService;
import com.nibir.hossain.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest
public class BeerControllerIT {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private BeerRepository beerRepository;
    @MockBean
    private BeerInventoryRepository beerInventoryRepository;
    @MockBean
    private BreweryService breweryService;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private BeerService beerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("mockuser")
    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void findBeersWithHttpBasic() throws Exception {
        mockMvc.perform(get("/beers/find").with(httpBasic("nibir", "hossain")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }
}