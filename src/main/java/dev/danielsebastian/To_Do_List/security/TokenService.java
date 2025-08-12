package dev.danielsebastian.To_Do_List.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.danielsebastian.To_Do_List.dto.user.JWTUserData;
import dev.danielsebastian.To_Do_List.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenService {
    @Value("${todolist.security.secret}")
    private String secret;

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId().toString())
                .withClaim("name", user.getUsername())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("API Librarymanager")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token){
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(
                    new JWTUserData(
                        UUID.fromString(jwt.getClaim("userId").asString()),
                        jwt.getClaim("name").asString(),
                        jwt.getSubject()
                    )
            );

        } catch (JWTVerificationException ex){
            return Optional.empty();
        }

    }
}
