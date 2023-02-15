package com.tinnovakovic.springboot.fluttermovierest.security

import com.tinnovakovic.springboot.fluttermovierest.model.ROLE_ADMIN
import com.tinnovakovic.springboot.fluttermovierest.model.ROLE_USER
import com.tinnovakovic.springboot.fluttermovierest.security.Filter.CustomAuthenticationFilter
import com.tinnovakovic.springboot.fluttermovierest.security.Filter.CustomerAuthorisationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val encoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.let {
            it.userDetailsService(userDetailsService)?.let {
                it.passwordEncoder(encoder)
            }
        }
    }

    // for tokens
    override fun configure(http: HttpSecurity?) {
        if (http != null) {
            val customAuthenticationFilter = CustomAuthenticationFilter(authenticationManagerBean())
            customAuthenticationFilter.setFilterProcessesUrl("/api/login")
            http.csrf().disable()
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/login/**", "/api/users/token/refresh/**").permitAll()
            http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority(ROLE_USER)
            http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/save/**").permitAll()
            http.authorizeRequests().anyRequest().authenticated()
            http.addFilter(customAuthenticationFilter)
            http.addFilterBefore(CustomerAuthorisationFilter(), UsernamePasswordAuthenticationFilter().javaClass)
        }
    }

    @Bean
    @Override
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}