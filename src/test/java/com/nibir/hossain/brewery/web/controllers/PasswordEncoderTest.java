package com.nibir.hossain.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/*
 * Created by Nibir Hossain on 18.12.20
 */
public class PasswordEncoderTest {
    private static final String PASSWORD = "password";

    @Test
    void testHashing() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String passwordWithSalt = PASSWORD + "SaltValue";
        System.out.println(DigestUtils.md5DigestAsHex(passwordWithSalt.getBytes()));
    }

    @Test
    void testNoop() {
        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        System.out.println(passwordEncoder.encode(PASSWORD));
    }
}
