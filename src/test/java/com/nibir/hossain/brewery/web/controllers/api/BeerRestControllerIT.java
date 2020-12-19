package com.nibir.hossain.brewery.web.controllers.api;

import com.nibir.hossain.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Created by Nibir Hossain on 18.12.20
 */

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerHttpBasic() throws Exception{
        mockMvc.perform(delete("/api/v1/beers/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .with(httpBasic("nibir", "hossain")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerHttpBasicWithUserRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beers/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerHttpBasicWithCustomerRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beers/" + UUID.randomUUID())
                .with(httpBasic("salma", "akther")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerNoAuth() throws Exception{
        mockMvc.perform(delete("/api/v1/beers/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beers")
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID())
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/656454")
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isOk());
    }
}
