package dev.cloudeko.zenei.domain.model.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenInput {
    private String refreshToken;
}
