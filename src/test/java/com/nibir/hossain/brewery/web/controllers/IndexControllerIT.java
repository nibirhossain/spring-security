package com.nibir.hossain.brewery.web.controllers;

/*
 * Created by Nibir Hossain on 18.12.20
 */

import com.nibir.hossain.brewery.repositories.BeerInventoryRepository;
import com.nibir.hossain.brewery.repositories.BeerRepository;
import com.nibir.hossain.brewery.repositories.CustomerRepository;
import com.nibir.hossain.brewery.services.BeerOrderService;
import com.nibir.hossain.brewery.services.BeerService;
import com.nibir.hossain.brewery.services.BreweryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class IndexControllerIT extends BaseIT {
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

    @MockBean
    private BeerOrderService beerOrderService;

    @Test
    void testGetIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
