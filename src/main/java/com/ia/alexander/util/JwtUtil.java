package com.ia.alexander.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.jwt.private.key}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken (Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        String userName = authentication.getPrincipal().toString();
        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + (60 * 60 * 1000));

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(userName)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    public DecodedJWT validateToken (String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();
            return jwtVerifier.verify(token);
        } catch(JWTVerificationException e){
            throw new JWTVerificationException("Invalid token, Not authorized");
        }
    }

    public String extractUsername (DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

}
