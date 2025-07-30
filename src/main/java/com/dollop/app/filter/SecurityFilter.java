package com.dollop.app.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dollop.app.exceptions.ExpiredTokenException;
import com.dollop.app.utils.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter  {
 
	 @Autowired
	 private JwtUtils jwtUtils;
	 
	 @Autowired
	 private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("Security filter called ");
		
		String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String tokenType= null;
         String userRole = null;
        System.out.println("authorizationHeader === >" +authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
         
        	System.out.println("if condition of security filter 1");
        	
        	token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        	System.out.println("Token ======>" +token);
        	
        	try {
//        		 tokenType = (String) jwtUtils.getHeader(token, "tokenType");
//            	 userRole = (String) jwtUtils.getHeader(token, "userRole");
            
                System.out.println("email of token === >" +username);
            } catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired: " + e.getMessage());
                throw new ExpiredTokenException("JWT Token has expired: ");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT Token: " + e.getMessage());
            } catch (SignatureException e) {
                System.out.println("JWT Signature does not match: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("JWT error: " + e.getMessage());
            }
        	
        	
        	System.out.println("tokenType ==== > " +tokenType);
        	System.out.println("User Role ==== >" +userRole);
        	
//        	if (tokenType.equals("ACCESS_TOKEN"))
//        	{
        	try {
                username = jwtUtils.extractUsername(token);
            
                System.out.println("email of token === >" +username);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired: " + e.getMessage());
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT Token: " + e.getMessage());
            } catch (SignatureException e) {
                System.out.println("JWT Signature does not match: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("JWT error: " + e.getMessage());
            }
//        }

        // If token is valid and user is not yet authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	System.out.println("if condition of security filter 2");
        	UserDetails userDetails = userDetailsService.loadUserByUsername(username);;

           System.out.println(" userDetails FROM SECURITY FILTER =================>  "+userDetails);
            // Validate token
            if (jwtUtils.validateToken(token, username)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                		username, userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        }
        filterChain.doFilter(request, response);
	}

}
