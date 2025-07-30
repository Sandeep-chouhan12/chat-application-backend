package com.dollop.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dollop.app.filter.SecurityFilter;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Autowired
	 private BCryptPasswordEncoder passwordEncoder;
	 @Autowired
	 private UserDetailsService userDetailsService;
	 @Autowired
	 private InvalidUserAthenticationPoint authenticationEntryPoint;
	 @Autowired
	 private SecurityFilter securityFilter;
	
	 
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception
	 {
		 System.out.println("3 "+ " authenticationManager");
		 return authConfig.getAuthenticationManager();
	 }
	 
	 @Bean
	 public DaoAuthenticationProvider authenticationProvider()
	 {
			System.out.println("1 " + "authenticationProvider");
			DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
			provider.setPasswordEncoder(passwordEncoder);
			provider.setUserDetailsService(userDetailsService);
			return provider;
	 }
	 
	 @Bean
	 public SecurityFilterChain configureAuth(HttpSecurity http) throws Exception
	 {
		 System.out.println("2"+ " configreAuth");
		 
		 return http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())  // Disable CSRF for API-based authentication
	            .authorizeHttpRequests(requests -> requests
	                .requestMatchers("/auth/**").permitAll()  // Allow login & signup APIs
	                 .anyRequest().authenticated()
	                ).exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint) // Customauthentication entry point
				
                    ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session
						
                    ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Add custom filter
                     .build();

//	        return http.build();
	 }
	 
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
	     CorsConfiguration  configuration = new CorsConfiguration();
	     configuration.setAllowedOrigins(List.of("http://192.168.1.8:4200")); // Angular origin
	     configuration.setAllowedMethods(List.of("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));
	     configuration.setAllowedHeaders(List.of("*"));
	     configuration.setAllowCredentials(true); // Important if you're using cookies or Authorization headers

	     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	     source.registerCorsConfiguration("/**", configuration);
	     return source;	
	 }
}
