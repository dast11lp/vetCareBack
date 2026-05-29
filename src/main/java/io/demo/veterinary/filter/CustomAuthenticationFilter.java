package io.demo.veterinary.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.demo.veterinary.entity.AppUser;
import io.demo.veterinary.jwt.JwtUtil;
import io.demo.veterinary.repository.AppUserRepository;
import io.demo.veterinary.service.MyUserDetailService;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	AuthenticationManager authenticationManager;

	JwtUtil jwtUtil;
	
	AppUserRepository userRepository;
	
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager,JwtUtil jwtUtil,AppUserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
	}
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		
		String email = request.getParameter("email");
		String password = obtainPassword(request);
		
		if(email != null) 
			email = email.trim();

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);
				

		return this.authenticationManager.authenticate(authenticationToken);
	}



	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal();

		AppUser appUser = this.userRepository.findByEmail(user.getUsername());

		String jwt = this.jwtUtil.jwtCreator(user.getUsername(), appUser.getId());

		response.setStatus(200);
		response.setHeader("Authorization", jwt);

		Map<String, String> tokens = new HashMap<>();
		tokens.put("Authorization", jwt);
		tokens.put("id", appUser.getId().toString());
		
		
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		Map<String, Object> body = new HashMap<String, Object>();

		body.put("message", "Error de autenticacion: username o password incorrecto");
		body.put("error", failed.getMessage());

		response.getWriter().write(new ObjectMapper().writeValueAsString(body)); 
		
		response.setStatus(401);
		response.setContentType("application/json");
		
		
	}

}







 
 