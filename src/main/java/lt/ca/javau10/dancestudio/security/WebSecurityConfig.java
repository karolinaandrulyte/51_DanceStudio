package lt.ca.javau10.dancestudio.security;

import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lt.ca.javau10.dancestudio.security.jwt.AuthTokenFilter;

import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	 @Autowired
	 UserDetailsService userDetailsService;
	
	 @Autowired
	 AuthenticationEntryPoint unauthorizedHandler;
	 
	 @Bean
	 AuthTokenFilter authenticationJwtTokenFilter() {
	    return new AuthTokenFilter();
	 }
	 
	 @Bean
	 DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(userDetailsService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	  }
	 
	 @Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }
	 
	 @Bean
	 PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	 
	 @Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	     http.csrf(csrf -> csrf.disable())
	     .cors(cors -> cors.configurationSource(request -> {
             CorsConfiguration configuration = new CorsConfiguration();
             configuration.setAllowedOrigins(List.of("http://localhost:3000"));
             configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
             configuration.setAllowCredentials(true);
             configuration.setAllowedHeaders(List.of("*"));
             return configuration;
         }))
	         .authorizeHttpRequests(auth -> 
	         auth.requestMatchers("/api/auth/**").permitAll()
             .requestMatchers("/api/users/students").hasAnyAuthority("ROLE_ADMIN", "ROLE_TEACHER") // Allow both roles
             .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN") // Ensure only admins can access other user management routes
             .requestMatchers("/api/test/*").authenticated()
             .anyRequest().permitAll()
	         )
	         .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	     http.authenticationProvider(authenticationProvider());
	     http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	     return http.build();
	 }
}
