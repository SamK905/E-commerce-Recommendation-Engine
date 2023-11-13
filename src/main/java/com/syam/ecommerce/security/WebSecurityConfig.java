package com.syam.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Permit all requests to the H2 console
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                // Disable CSRF (Cross-Site Request Forgery) for the H2 console
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                // Disable frame options for the H2 console
                .headers().frameOptions().disable();

        // Other security configurations...
    }
}
