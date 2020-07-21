package com.iot.platform.Config;

import com.iot.platform.Provider.JwtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/v1.0/user/signin").permitAll()
                .antMatchers("/api/v1.0/user/reset-password-token").permitAll()
                .antMatchers("/api/v1.0/user/update-password-by-token").permitAll().antMatchers("/v2/api-docs")
                .permitAll().antMatchers("/webjars/**").permitAll().antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/auth/login");
        http.apply(new JwtFilterConfig(jwtProvider));
    }

}