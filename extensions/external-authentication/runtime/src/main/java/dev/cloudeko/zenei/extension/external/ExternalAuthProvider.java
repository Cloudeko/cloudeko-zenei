package dev.cloudeko.zenei.extension.external;

import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.extension.external.web.client.ExternalAccessToken;

public interface ExternalAuthProvider {
    ExternalUserProfile getExternalUserProfile(ExternalAccessToken accessToken);

    ExternalAuthProviderConfig config();

    String getAuthorizationEndpoint();

    String getTokenEndpoint();

    String getBaseEndpoint();
}
