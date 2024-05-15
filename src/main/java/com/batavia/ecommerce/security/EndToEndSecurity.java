package com.batavia.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class EndToEndSecurity {
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(
                        "/",
                        "/browse",
                        "/images/**",
                        "/css/",
                        "/js/",
                        "/auth/**",
                        "/error",
                        "/login",
                        "/registration")
                    .permitAll()
                    .requestMatchers(
                        "/check-out",
                        "/user/**",
                        "/api")
                    .hasAuthority("[ROLE_USER]")
                    .requestMatchers(
                        "/admin/**",
                        "/product/**")
                    .hasAuthority("[ROLE_ADMIN]")
                    .anyRequest().authenticated()
                )
            .formLogin((login) -> login
                    .loginPage("/login").usernameParameter("email")
                    .defaultSuccessUrl("/", true).permitAll()
                )
            .logout((logout) -> logout
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll()
                )
            .exceptionHandling((exception)-> exception.accessDeniedPage("/access-denied"))
            .csrf(Customizer.withDefaults()
            );

            return http.build();
    }

}
