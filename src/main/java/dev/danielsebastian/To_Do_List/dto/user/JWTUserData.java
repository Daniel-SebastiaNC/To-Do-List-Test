package dev.danielsebastian.To_Do_List.dto.user;

import com.auth0.jwt.interfaces.Claim;

import java.util.UUID;

public record JWTUserData(UUID id,
                          String name,
                          String email) {
}
