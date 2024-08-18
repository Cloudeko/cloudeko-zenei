package dev.cloudeko.zenei.domain.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;

    private String type;
    private String provider;
    private String providerId;

    private List<ExternalAccessToken> accessTokens;

    private String sessionState;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
