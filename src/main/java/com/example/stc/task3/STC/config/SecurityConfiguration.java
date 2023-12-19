package com.example.stc.task3.STC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails mostafa = User.builder()
                .username("mostafa@stc")
                .password("{bcrypt}$2a$10$cuPV1HTMi5Rrun7K08ydJeuhEkhRRPjl7Ufku5CvcfW41Toh6sfdS") //123
                .roles("EDIT")
                .build();
        UserDetails ahmed = User.builder()
                .username("ahmed@stc")
                .password("{bcrypt}$2a$10$cuPV1HTMi5Rrun7K08ydJeuhEkhRRPjl7Ufku5CvcfW41Toh6sfdS") //123
                .roles("VIEW")
                .build();
        return new InMemoryUserDetailsManager(mostafa, ahmed);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.GET, "/createSpace/{space}").hasRole("EDIT")
                .requestMatchers(HttpMethod.GET, "/{space}/createFolder/{folder}").hasRole("EDIT")
                .requestMatchers(HttpMethod.POST, "/{space}/{folder}/upload").hasRole("EDIT")
                .requestMatchers(HttpMethod.POST, "/{space}/upload").hasRole("EDIT")
                .requestMatchers(HttpMethod.GET, "/{space}").hasAnyRole("EDIT", "VIEW")
                .requestMatchers(HttpMethod.GET, "/{space}/{folder}").hasAnyRole("EDIT", "VIEW")
                .requestMatchers(HttpMethod.GET, "/download/{fileId}").hasAnyRole("EDIT", "VIEW")

        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

}
