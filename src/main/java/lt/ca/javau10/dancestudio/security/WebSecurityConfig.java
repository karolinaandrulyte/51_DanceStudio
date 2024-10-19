package lt.ca.javau10.dancestudio.security;

import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
         auth.requestMatchers("/api/auth/**").permitAll() // Allow access to auth endpoints
             .requestMatchers(HttpMethod.GET, "/api/users/students").hasAnyAuthority("ROLE_ADMIN", "ROLE_TEACHER") // Allow both roles to view students
             .requestMatchers(HttpMethod.GET, "/api/users/teachers/{teacherId}/students").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN") // Allow teachers to view assigned students
             .requestMatchers(HttpMethod.PUT, "/api/users/students/{studentId}/assign/{teacherId}").hasAuthority("ROLE_TEACHER") // Allow only teachers to assign students
             .requestMatchers(HttpMethod.PUT, "/api/users/students/{studentId}/unassign").hasAuthority("ROLE_TEACHER") // Only teachers can unassign students
             .requestMatchers(HttpMethod.PUT, "/api/users/profile/update").hasAuthority("ROLE_TEACHER")
             .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN") // Ensure only admins can access other user management routes
             .requestMatchers("/api/test/*").authenticated() // Require authentication for test routes
             .anyRequest().permitAll() // Allow all other requests
     )
	         .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	     http.authenticationProvider(authenticationProvider());
	     http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	     return http.build();
	 }
}
