package com.epam.katowice.configuration.security;


import com.epam.katowice.controller.MovieRentalController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    static final int COOKIE_VALIDITY_TIME_IN_SECONDS = 28800;
    static final String REMEMBER_ME_COOKIE = "remember-me";

    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .antMatchers("/", "/webjars/**").permitAll()
                .antMatchers(MovieRentalController.NEW_MOVIE_ENDPOINT).hasRole(Role.ADMIN.name())
                .and()
            .formLogin()
                .loginPage(MovieRentalController.LOGIN_ENDPOINT)
                .permitAll()
                .and()
            .logout()
                .deleteCookies(REMEMBER_ME_COOKIE)
                .permitAll()
                .and()
            .httpBasic()
            .and()
            .csrf()
            .and()
            .rememberMe()
                .rememberMeCookieName(REMEMBER_ME_COOKIE)
                .tokenValiditySeconds(COOKIE_VALIDITY_TIME_IN_SECONDS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
