package com.poc.cric.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    
    /**
     * Selectively secures of Delete EndPoint api/v1/secure/players/delete endpoint with Basic Auth
     * Can be enhanced for 
     * Other endpoints(PUT,GET,POST) are not secured
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
         .csrf().disable()
         .authorizeRequests()
             .antMatchers("/h2-console/**").permitAll()
             .and()
         .authorizeRequests()
             .antMatchers("/").permitAll()
             .and()
         .authorizeRequests()
             .antMatchers("/api/v1/secure/players/*").authenticated()
             .antMatchers("/players/*").permitAll()
         .and()
         .httpBasic();
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.inMemoryAuthentication()
            .withUser("jiten")
            .password("$2a$04$NIeUsk9y.huk0Ve557FzV.z4yQ0okPHWI11iBtyfJd7cSXyIuoOLC")
            .roles("USER");
    }
    
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
