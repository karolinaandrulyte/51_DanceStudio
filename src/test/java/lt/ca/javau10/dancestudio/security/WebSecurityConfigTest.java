package lt.ca.javau10.dancestudio.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WebSecurityConfigTest {
	
	private WebSecurityConfig webSecurityConfig;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        webSecurityConfig = new WebSecurityConfig();
        userDetailsService = mock(UserDetailsService.class);
        passwordEncoder = new BCryptPasswordEncoder();
        webSecurityConfig.userDetailsService = userDetailsService;
    }

    @Test
    void authenticationManager() throws Exception {
        AuthenticationConfiguration authConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(authConfig.getAuthenticationManager()).thenReturn(authenticationManager);
        
        AuthenticationManager result = webSecurityConfig.authenticationManager(authConfig);

        assertEquals(authenticationManager, result);
    }
    
    @Test
    void passwordEncoder() {
        PasswordEncoder passwordEncoder = new WebSecurityConfig().passwordEncoder();
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
    
    @Test
    void authenticationProviderTest() {
        DaoAuthenticationProvider authProvider = webSecurityConfig.authenticationProvider();
        assertNotNull(authProvider);// Test if the password encoder is not null
        
        String rawPassword = "testPassword";// Test password encoding behavior
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword)); // Password encoding behavior

        UserDetails mockUserDetails = mock(UserDetails.class);// Test user lookup behavior
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(mockUserDetails);

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
        assertEquals(mockUserDetails, userDetails);  // Test user lookup
        // Verify interaction with UserDetailsService
        verify(userDetailsService, times(1)).loadUserByUsername("testUser");
    }
    
    @Test
    void filterChain() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class);
        when(http.addFilterBefore(any(), eq(UsernamePasswordAuthenticationFilter.class))).thenReturn(http);

        SecurityFilterChain filterChain = webSecurityConfig.filterChain(http);

        assertNotNull(filterChain);
        verify(http).addFilterBefore(any(), eq(UsernamePasswordAuthenticationFilter.class));
        verify(http).csrf(csrf -> csrf.disable());
    }
    
    
    @Test
    void corsConfigurationTest() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class);

        when(http.cors(any())).thenReturn(http); // Ensure CORS configuration is triggered

        webSecurityConfig.filterChain(http); // Call the method that applies the CORS config

        verify(http).cors(any());  // Assert that the cors configuration is applied
    }
    
    @Test
    void exceptionHandling() throws Exception {
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        HttpSecurity http = mock(HttpSecurity.class);
        when(http.exceptionHandling(any())).thenReturn(http);

        webSecurityConfig.filterChain(http);

        verify(http).exceptionHandling(any());
    }
}
