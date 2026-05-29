package io.demo.veterinary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.demo.veterinary.filter.CustomAuthenticationFilter;
import io.demo.veterinary.filter.CustomAuthorizationFilter;
import io.demo.veterinary.jwt.JwtUtil;
import io.demo.veterinary.repository.AppUserRepository;


@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Autowired 
	private CustomAuthorizationFilter authorizationFilter;
	
	@Autowired
	private AppUserRepository userRepository;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		
		http.authorizeHttpRequests().antMatchers("/auth/**", "/owner/register").permitAll();
		http.addFilter(new CustomAuthenticationFilter( authenticationManager,jwtUtil, userRepository));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeHttpRequests().anyRequest().permitAll();
		http.csrf().disable();	
		http.cors().and().headers();
		
		http.addFilterBefore(authorizationFilter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();

	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
