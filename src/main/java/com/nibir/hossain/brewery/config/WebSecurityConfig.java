package com.nibir.hossain.brewery.config;

/*
 * Created by Nibir Hossain on 18.12.20
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new LdapShaPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           .authorizeRequests(authorize -> {
               authorize
                       .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                       .antMatchers("/beers/find", "/beers*").permitAll()
                       .antMatchers(HttpMethod.GET, "/api/v1/beers/**").permitAll().antMatchers();
               authorize.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
           })
           .authorizeRequests()
           .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();
    }

    /**
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("nibir")
                .password("hossain")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("sajib")
                .password("mohammad")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("nibir")
                .password("{SSHA}h2UayQ1lKDGaV17RsSK5PbXJnMW70agT3C3fCA==")
                .roles("ADMIN")
                .and()
                .withUser("sajib")
                .password("{SSHA}MoN7jOyOUatD+s8MAyaYTvA9RBhbxoTIQdiEWA==")
                .roles("USER");
    }
}
