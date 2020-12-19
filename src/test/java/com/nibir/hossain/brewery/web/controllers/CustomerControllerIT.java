package com.nibir.hossain.brewery.web.controllers;

/*
 * Created by Nibir Hossain on 19.12.20
 */

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomerControllerIT extends BaseIT {
    @Test
    void testListCustomersWithCustomerRole() throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic("salma", "akther")))
                .andExpect(status().isOk());

    }

    @Test
    void testListCustomersWithAdminRole() throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic("nibir", "hossain")))
                .andExpect(status().isOk());

    }

    @Test
    void testListCustomersWithUserRole() throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic("sajib", "mohammad")))
                .andExpect(status().isForbidden());

    }

    @Test
    void testListCustomersNOTLOGGEDIN() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());

    }

    @DisplayName("Add Customers")
    @Nested
    class AddCustomers {
        @Rollback
        void processCreationFormNOTAUTH() throws Exception{
            mockMvc.perform(post("/customers/new")
                    .param("customerName", "Foo Customer2")
                    .with(httpBasic("sajib", "mohammad")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void processCreationFormNOAUTH() throws Exception{
            mockMvc.perform(post("/customers/new")
                    .param("customerName", "Foo Customer"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
