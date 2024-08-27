package com.pi.mafu_bakery_api.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pi.mafu_bakery_api.dto.TokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.auth0.jwt.JWT;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${api.token.secret.key}")
    private String secretKey = "secret";

    @Value("${api.ecommerce.token.expiration:3600000}")
    private long validityToken = 3600000; // 1hr

    @Autowired
    private UserDetailsService userDetailsService;

    public TokenDTO createAccessToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityToken);
        var accessToken = generateToken(email, now, validity);

        return new TokenDTO(email, true, now, validity, accessToken);
    }

    private String generateToken(String email, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return JWT.create()
                .withClaim("Permissão", "TESTE")
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withIssuer(issuerUrl)
                .withSubject(email)
                .sign(algorithm);
    }

    public String validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return JWT.require(algorithm)
                    .withIssuer(issuerUrl)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return null;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if(header == null) return null;
        return header.replace("Bearer ", "");
    }
}
