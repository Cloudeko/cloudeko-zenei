package dev.cloudeko.zenei.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.cloudeko.zenei.extension.core.model.session.SessionToken;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@RegisterForReflection
@Schema(name = "SessionToken", description = "Represents a token response")
public class SessionTokenResponse {

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

    public SessionTokenResponse(SessionToken sessionToken) {
        this.accessToken = sessionToken.getAccessToken();
        this.tokenType = sessionToken.getTokenType();
        this.expiresIn = sessionToken.getExpiresIn();
        this.refreshToken = sessionToken.getRefreshToken();
    }
}
