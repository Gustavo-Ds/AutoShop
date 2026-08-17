package br.com.ssdev.autoshop.config.security;

import br.com.ssdev.autoshop.exceptions.InvalidTokenException;
import br.com.ssdev.autoshop.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${JWT:my-secret-key-123456}")
    private String jwtSecret;


    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        try{
            return JWT.create()
                    .withIssuer("AutoShop")
                    .withExpiresAt(generateExpires())
                    .withSubject(user.getEmail())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException("Unable to generate the token");
        }
    }

    public String validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        try{
            return JWT.require(algorithm)
                    .withIssuer("AutoShop")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            throw new InvalidTokenException("Invalid Token");
        }
    }
    public Instant generateExpires(){
        return LocalDateTime
                .now()
                .plusHours(3)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
