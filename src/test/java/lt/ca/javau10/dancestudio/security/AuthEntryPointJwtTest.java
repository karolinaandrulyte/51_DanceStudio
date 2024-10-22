package lt.ca.javau10.dancestudio.security;

import static org.mockito.Mockito.*;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

public class AuthEntryPointJwtTest {

    private AuthEntryPointJwt authEntryPointJwt;

    @BeforeEach
    void setUp() {
        authEntryPointJwt = new AuthEntryPointJwt();
    }

    @Test
    void testCommence() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        when(authException.getMessage()).thenReturn("Unauthorized");

        authEntryPointJwt.commence(request, response, authException);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).getOutputStream();
    }
}
