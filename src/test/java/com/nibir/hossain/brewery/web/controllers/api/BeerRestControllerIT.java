package com.nibir.hossain.brewery.web.controllers.api;

import com.nibir.hossain.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Created by Nibir Hossain on 18.12.20
 */

@WebMvcTest
public class BeerRestControllerIT extends BaseIT {
    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beers"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/656454"))
                .andExpect(status().isOk());
    }
}
