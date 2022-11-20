package com.example.restfulservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.servlet.configuration.WebMvcSecurityConfiguration;

@Configuration
public class SecurityConfig extends WebMvcSecurityConfiguration {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("Kenneth")
                    .password("{noop}test1234")
                    .roles("USER");
        }
}
