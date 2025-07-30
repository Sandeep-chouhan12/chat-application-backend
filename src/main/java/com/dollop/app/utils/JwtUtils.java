package com.dollop.app.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dollop.app.enums.OtpType;
import com.dollop.app.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	@Value("${app.secret}")
	  private String secret;
	  
	  public String extractUsername(String token)
	  {  
		  return getClaims(token).getSubject();
	  }
	  
	  public String generateToken(String subject,String userId)
	  {
		  System.out.println("get token called 1");
		  Map<String,Object> claims= new HashMap<>();
	      claims.put("userId", userId);
		  return generateToken(claims,subject);
	  }
	  
	  private Claims getClaims(String token) {
		  return Jwts.parser()
				   .setSigningKey(secret.getBytes())
				   .parseClaimsJws(token).getBody();
	    }
	  
	  public boolean validateToken(String token, String username) {
	        return (extractUsername(token).equals(username) && !isTokenExpired(token));
	    }
	  
	  private boolean isTokenExpired(String token) {
	        return extractClaim(token, Claims::getExpiration).before(new Date());
	    }
	  
	  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = Jwts.parser()
	                .setSigningKey(secret.getBytes() )  // ✅ Correct parsing method for jjwt 0.9.1
	                .parseClaimsJws(token)
	                .getBody();
	        return claimsResolver.apply(claims);
	    }
	  
	  private String generateToken(Map<String,Object> claims,String subject) {
		  System.out.println("get token called 1");
		  return Jwts.builder()
	           .setClaims(claims)
	           .setSubject(subject)
	           .setIssuedAt(new Date(System.currentTimeMillis()))
	           
	           .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(9)))
			   
	           .setIssuer("ChatApplication")
	           .signWith(SignatureAlgorithm.HS256,secret.getBytes())
	           .compact();	
	  }
	  public Object getHeader(String token,String key)
	  {  
		  return getClaims(token).get(key);
	  }

	public String generateAccessToken(String subject, String userId) {
		System.out.println("get token called 1");
		  Map<String,Object> claims= new HashMap<>();
//		  if(tokenType.equals(TokenType.AUTH_TOKEN))
//		  {
//		       claims.put("otpType", otpType);
//		  }
//		  claims.put("userRole", userRole);
//		  claims.put("tokenType",tokenType);
		  claims.put("userId", userId);
		  
		  return generateToken(claims,subject);
	}
	  
}