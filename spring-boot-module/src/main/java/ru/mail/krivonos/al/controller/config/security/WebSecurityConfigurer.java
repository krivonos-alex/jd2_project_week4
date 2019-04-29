package ru.mail.krivonos.al.controller.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.mail.krivonos.al.controller.config.handler.AppAccessDeniedHandler;
import ru.mail.krivonos.al.controller.config.handler.AppAuthenticationSuccessHandler;

import static ru.mail.krivonos.al.controller.constant.RoleConstants.ADMIN_ROLE_NAME;
import static ru.mail.krivonos.al.controller.constant.RoleConstants.CUSTOMER_ROLE_NAME;
import static ru.mail.krivonos.al.controller.constant.URLConstants.*;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfigurer(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ITEMS_PAGE_URL)
                .hasAuthority(CUSTOMER_ROLE_NAME)
                .antMatchers(USERS_PAGE_URL)
                .hasAuthority(ADMIN_ROLE_NAME)
                .antMatchers(DEFAULT_PAGE_URL, ABOUT_PAGE_URL, LOGIN_PAGE_URL, LOGOUT_PAGE_URL,
                        ERROR_403_PAGE_URL, BOOTSTRAP_CONTENT_URL)
                .permitAll()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE_URL)
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler();
    }

    @Bean("appAccessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new AppAccessDeniedHandler();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
