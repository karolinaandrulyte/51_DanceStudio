package lt.ca.javau10.dancestudio.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lt.ca.javau10.dancestudio.security.JwtUtils;

public class AuthTokenFilter extends OncePerRequestFilter {

	 @Autowired
	 private JwtUtils jwtUtils;
	 
	 @Autowired
	 UserDetailsService userService;
	 
	 private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	 @Override
	 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	         throws ServletException, IOException {
	     String jwt = parseJwt(request);
	     if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
	         Authentication authentication = jwtUtils.getAuthentication(jwt);
	         if (authentication != null) {
	             SecurityContextHolder.getContext().setAuthentication(authentication);
	             logger.debug("Authentication set: " + SecurityContextHolder.getContext().getAuthentication());
	         } else {
	             logger.debug("Authentication is null, not setting SecurityContext");
	         }
	     }
	     filterChain.doFilter(request, response);
	 }
	
	private String parseJwt(HttpServletRequest request) {
	    String headerAuth = request.getHeader("Authorization");

	    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	      return headerAuth.substring(7);
	    }

	    return null;
	}
}
