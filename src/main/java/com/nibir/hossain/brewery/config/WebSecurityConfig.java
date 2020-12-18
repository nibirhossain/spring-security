package com.nibir.hossain.brewery.config;

/*
 * Created by Nibir Hossain on 18.12.20
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           .authorizeRequests(authorize -> {
               authorize.antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll();
               authorize.antMatchers("/beers/find", "/beers*").permitAll();
           })
           .authorizeRequests()
           .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();

    }
}
