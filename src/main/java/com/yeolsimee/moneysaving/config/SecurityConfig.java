package com.yeolsimee.moneysaving.config;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.user.service.*;
import com.yeolsimee.moneysaving.filter.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final FirebaseAuth firebaseAuth;
    private final UserService userService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .formLogin().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authorize) ->
                    authorize.antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/v1/login/**").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/v1/signin").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/v1/user/recovery").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/v1/routine").permitAll()
                            .antMatchers(HttpMethod.GET, "/healthcheck").permitAll()
                            .antMatchers(HttpMethod.PUT, "/api/v1/routinecheck/*").permitAll()
                            .anyRequest().authenticated()
            );
        http.addFilterBefore(new JwtFilter(firebaseAuth, userService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}