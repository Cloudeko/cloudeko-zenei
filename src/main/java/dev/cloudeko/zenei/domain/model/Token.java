package dev.cloudeko.zenei.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Token", description = "Represents a token")
public class Token {

    @Schema(description = "Access token")
    private String accessToken;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;

    @Schema(description = "Time in seconds until the token expires")
    private Integer expiresIn;

    @Schema(description = "Refresh token")
    private String refreshToken;
}
