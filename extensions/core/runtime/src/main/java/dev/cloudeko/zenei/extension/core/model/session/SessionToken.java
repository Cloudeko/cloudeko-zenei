package dev.cloudeko.zenei.extension.core.model.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionToken {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
}
