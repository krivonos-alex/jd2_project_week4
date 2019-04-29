package ru.mail.krivonos.al.controller.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.mail.krivonos.al.controller.config.handler.ApiAccessDeniedHandler;

import static ru.mail.krivonos.al.controller.constant.RoleConstants.ADMIN_ROLE_NAME;
import static ru.mail.krivonos.al.controller.constant.URLConstants.API_USERS_PAGE_URL;

@Configuration
@Order(1)
public class ApiSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiSecurityConfigurer(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher(API_USERS_PAGE_URL)
                .authorizeRequests()
                .anyRequest()
                .hasAuthority(ADMIN_ROLE_NAME)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean("apiAccessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new ApiAccessDeniedHandler();
    }
}
