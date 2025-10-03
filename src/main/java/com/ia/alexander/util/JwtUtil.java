package com.ia.alexander.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.jwt.private.key}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;


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

    public String extractEmail (DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

}
