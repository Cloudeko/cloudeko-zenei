package dev.cloudeko.zenei.domain.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;

    private String type;
    private String provider;
    private String providerId;

    private String refreshToken;
    private String accessToken;
    private Long accessTokenExpiresAt;

    private String tokenType;
    private String scope;

    private String idToken;
    private String sessionState;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
