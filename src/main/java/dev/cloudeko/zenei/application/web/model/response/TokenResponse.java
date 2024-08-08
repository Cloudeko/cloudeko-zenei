package dev.cloudeko.zenei.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.cloudeko.zenei.domain.model.Token;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@RegisterForReflection
@Schema(name = "Token", description = "Represents a token response")
public class TokenResponse {

    @JsonProperty("access_token")
    @Schema(description = "Access token for the user")
    private String accessToken;

    @JsonProperty("token_type")
    @Schema(description = "Type of the token")
    private String tokenType;

    @JsonProperty("expires_in")
    @Schema(description = "Time in seconds until the token expires")
    private int expiresIn;

    @JsonProperty("refresh_token")
    @Schema(description = "Refresh token for the user")
    private String refreshToken;

    public TokenResponse(Token token) {
        this.accessToken = token.getAccessToken();
        this.tokenType = token.getTokenType();
        this.expiresIn = token.getExpiresIn();
        this.refreshToken = token.getRefreshToken();
    }
}
