package lt.ca.javau10.dancestudio.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.services.UserService;

import java.util.Date;
import java.lang.reflect.Field;

public class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private UserService userService;
    private String jwtSecret;

    @BeforeEach
    void setUp() throws Exception {
        userService = mock(UserService.class);
        jwtUtils = new JwtUtils();

        // Access the private field jwtSecret via reflection
        Field jwtSecretField = JwtUtils.class.getDeclaredField("jwtSecret");
        jwtSecretField.setAccessible(true);  // Makes the private field accessible
        jwtSecretField.set(jwtUtils, "TestSecretKeyThatShouldBeVeryLong1234567890");

        jwtUtils.setExpirationMs(1000000);
        jwtUtils.userService = userService;
        
        jwtSecret = (String) jwtSecretField.get(jwtUtils);
    }
    
    @Test
    void testJwtSecretAccess() throws Exception {
        // Access the private field jwtSecret via reflection
        Field jwtSecretField = JwtUtils.class.getDeclaredField("jwtSecret");
        jwtSecretField.setAccessible(true);  // Makes the private field accessible

        // Set the value of the private field
        jwtSecretField.set(jwtUtils, "myTestSecret");

        // Verify if jwtSecret has been correctly set
        assertEquals("myTestSecret", jwtSecretField.get(jwtUtils));
    }


    @Test
    void testGenerateJwtToken() {
        UserDto userDetails = mock(UserDto.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetUserNameFromJwtToken() {
        String token = Jwts.builder()
            .setSubject("testUser")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000000))
            .signWith(jwtUtils.toKey(jwtSecret)) // Use the jwtSecret obtained via reflection
            .compact();

        String username = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals("testUser", username);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testValidateJwtToken() {
        String token = Jwts.builder()
            .setSubject("testUser")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000000))
            .signWith(jwtUtils.toKey(jwtSecret)) // Use the jwtSecret obtained via reflection
            .compact();

        boolean isValid = jwtUtils.validateJwtToken(token);

        assertTrue(isValid);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAuthentication() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userService.loadUserByUsername("testUser")).thenReturn(userDetails);

        String token = Jwts.builder()
            .setSubject("testUser")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000000))
            .signWith(jwtUtils.toKey(jwtSecret)) // Use the jwtSecret obtained via reflection
            .compact();

        Authentication auth = jwtUtils.getAuthentication(token);

        assertNotNull(auth);
        assertEquals("testUser", auth.getPrincipal());
    }

}
