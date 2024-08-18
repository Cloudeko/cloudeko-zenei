package dev.cloudeko.zenei.application.web.model.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RegisterForReflection
@Schema(name = "External Access Token List", description = "Represents a list of external access tokens")
public class ExternalAccessTokensResponse extends ArrayList<ExternalAccessTokenResponse> {

    public ExternalAccessTokensResponse(List<ExternalAccessTokenResponse> externalAccessTokens) {
        super(externalAccessTokens);
    }
}
