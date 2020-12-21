package com.nibir.hossain.brewery.web.controllers.api;

import com.nibir.hossain.brewery.domain.Beer;
import com.nibir.hossain.brewery.repositories.BeerRepository;
import com.nibir.hossain.brewery.web.controllers.BaseIT;
import com.nibir.hossain.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
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

    @Autowired
    private BeerRepository beerRepository;

    private Beer beerToDelete() {
        Random rand = new Random();

        return beerRepository.saveAndFlush(Beer.builder()
                .beerName("Delete Me Beer")
                .beerStyle(BeerStyleEnum.IPA)
                .minOnHand(12)
                .quantityToBrew(200)
                .upc(String.valueOf(rand.nextInt(99999999)))
                .build());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception{
        mockMvc.perform(delete("/api/v1/beers/"+ beerToDelete().getId())
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
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beers/" + beer.getId())
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beerUpc/" + beer.getUpc())
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isOk());
    }
}
