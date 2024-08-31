package com.pi.mafu_bakery_api.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pi.mafu_bakery_api.dto.TokenDTO;
import com.pi.mafu_bakery_api.enums.RoleEnum;
import com.pi.mafu_bakery_api.repository.CredencialRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.auth0.jwt.JWT;

import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${api.token.secret.key}")
    private String secretKey = "secret";

    @Value("${api.ecommerce.token.expiration:3600000}")
    private long validityToken = 3600000; // 1hr

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CredencialRepository credencialRepository;

    public TokenDTO createAccessToken(String email) {
        //TODO: Fazer a conversão das datas com o Fuso Horário do BR.
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityToken);
        RoleEnum permissao = credencialRepository.findPermissaoByEmail(email);
        var accessToken = generateToken(email, permissao, now, validity);

        return new TokenDTO(email, true, permissao, now, validity, accessToken);
    }

    private String generateToken(String email, RoleEnum permissao, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return JWT.create()
                .withClaim("Permissão", permissao.name())
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
