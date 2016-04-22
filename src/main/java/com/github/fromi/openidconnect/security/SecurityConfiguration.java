package com.github.fromi.openidconnect.security;

import static org.springframework.http.HttpMethod.GET;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String LOGIN_URL = "/login";

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint(LOGIN_URL);
    }
    
//    @Bean
//    public AuthenticationSuccessHandler successHandler() {
//    	return new RdfAuthenticationSuccessHandler();
//    }

    @Bean
    public OpenIDConnectAuthenticationFilter openIdConnectAuthenticationFilter() {
    	OpenIDConnectAuthenticationFilter open = new OpenIDConnectAuthenticationFilter(LOGIN_URL);
//    	open.setAuthenticationSuccessHandler(successHandler());
        return open;
    }

    @Bean
    public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(oAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAfter(openIdConnectAuthenticationFilter(), OAuth2ClientContextFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and().authorizeRequests()
                .antMatchers(GET, "/").permitAll()
                .antMatchers(GET, "/test").authenticated();
    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//      http.authorizeRequests()
//        .antMatchers("/", "/home").access("hasRole('USER')")
//        .antMatchers("/admin/**").access("hasRole('ADMIN')")
//        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//        .and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
//        .usernameParameter("ssoId").passwordParameter("password")
//        .and().csrf()
//        .and().exceptionHandling().accessDeniedPage("/Access_Denied");
//    }
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN","DBA");
//    }
}
