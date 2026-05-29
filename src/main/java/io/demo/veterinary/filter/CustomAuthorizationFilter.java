package io.demo.veterinary.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.demo.veterinary.jwt.JwtUtil;

import java.util.List;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			try {
				DecodedJWT decoded = this.jwtUtil.jwtVerifier(jwt);
				String email = decoded.getClaim("email").asString();
				Long userId = decoded.getClaim("userId").asLong();
				String role = decoded.getClaim("role").asString();

				var auth = new UsernamePasswordAuthenticationToken(
						email,
						null,
						role != null ? List.of(new SimpleGrantedAuthority(role)) : List.of()
				);
				auth.setDetails(userId); // userId disponible en el contexto sin tocar la BD

				SecurityContextHolder.createEmptyContext();
				var context = SecurityContextHolder.createEmptyContext();
				context.setAuthentication(auth);
				SecurityContextHolder.setContext(context);

			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}