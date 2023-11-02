package com.portfolio.appPortfolio.security;


import com.portfolio.appPortfolio.exception.AuthEntryPoint;
import com.portfolio.appPortfolio.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AdminService adminService;
    @Autowired
    AuthenticationFilter authenticationFilter;
    @Autowired
    AuthEntryPoint authEntryPoint;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/files/downloadFile/{fileName:.+}").
                permitAll().antMatchers(HttpMethod.GET, "/portfolioApp/projects").
                permitAll().antMatchers(HttpMethod.GET, "/portfolioApp/project/{projectId}").
                permitAll().antMatchers(HttpMethod.GET, "/portfolioApp/articles").
                permitAll().antMatchers(HttpMethod.GET, "/portfolioApp/article/{articleId}").
                permitAll().anyRequest().authenticated().and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint).and().addFilterBefore(authenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration config= new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception {
     auth.userDetailsService(adminService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager
    getAuthenticationManager() throws Exception {
        return authenticationManager();
    }

}
