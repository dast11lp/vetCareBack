package io.demo.veterinary.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	private Algorithm algorithm() {
		return Algorithm.HMAC256(secret);
	}

	public String jwtCreator(String email, Long userId) {
		return JWT.create()
				.withSubject("User Details")
				.withClaim("email", email)
				.withClaim("userId", userId)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + expiration))
				.withIssuer("veterinary")
				.sign(algorithm());
	}

	public DecodedJWT jwtVerifier(String token) {
		JWTVerifier verifier = JWT.require(algorithm())
				.withSubject("User Details")
				.withIssuer("veterinary")
				.build();
		return verifier.verify(token);
	}
}
