package com.lm.usersservice.security;

import com.lm.usersservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author el_le
 * @since 09/03/2022 22:02
 */

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebConfig(
            Environment env,
            UserService usersService,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/users/**")
                .permitAll().and().addFilter(getAuthenticationFilter());
        http.headers()
                .frameOptions()
                .disable();
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter auth = new AuthenticationFilter(userService, env, authenticationManager());
//        auth.setAuthenticationManager();
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
