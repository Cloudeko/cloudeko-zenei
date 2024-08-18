package dev.cloudeko.zenei.domain.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAccessToken {
    private String idToken;
    private String refreshToken;
    private String accessToken;
    private String tokenType;
    private String scope;
    private Long accessTokenExpiresAt;
}
