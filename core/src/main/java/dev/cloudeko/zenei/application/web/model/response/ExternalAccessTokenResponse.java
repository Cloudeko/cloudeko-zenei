package dev.cloudeko.zenei.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.cloudeko.zenei.domain.model.account.ExternalAccessToken;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@RegisterForReflection
@Schema(name = "External Access Token", description = "Represents an access token from an external provider")
public class ExternalAccessTokenResponse {

    @JsonProperty("id_token")
    @Schema(description = "ID token")
    private String idToken;

    @JsonProperty("refresh_token")
    @Schema(description = "Refresh token")
    private String refreshToken;

    @JsonProperty("access_token")
    @Schema(description = "Access token")
    private String accessToken;

    @JsonProperty("token_type")
    @Schema(description = "Token type")
    private String tokenType;

    @JsonProperty("scope")
    @Schema(description = "Scope")
    private String scope;

    @JsonProperty("access_token_expires_at")
    private Long accessTokenExpiresAt;

    public ExternalAccessTokenResponse(ExternalAccessToken externalAccessToken) {
        this.idToken = externalAccessToken.getIdToken();
        this.refreshToken = externalAccessToken.getRefreshToken();
        this.accessToken = externalAccessToken.getAccessToken();
        this.tokenType = externalAccessToken.getTokenType();
        this.scope = externalAccessToken.getScope();
        this.accessTokenExpiresAt = externalAccessToken.getAccessTokenExpiresAt();
    }
}
