package io.github.igormateus.repertapp.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.github.igormateus.repertapp.api.security.JWTAuthenticationFilter;
import io.github.igormateus.repertapp.api.security.JWTValidationFilter;
import io.github.igormateus.repertapp.domain.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableWebSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable() // TODO: enable to production
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTValidationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        final var source = new UrlBasedCorsConfigurationSource();

        var corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        // Enable access for all requests
        // TODO: Change in production
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
