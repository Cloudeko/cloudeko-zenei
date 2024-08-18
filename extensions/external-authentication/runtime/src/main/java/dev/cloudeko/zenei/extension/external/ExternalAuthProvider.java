package dev.cloudeko.zenei.extension.external;

import dev.cloudeko.zenei.extension.external.config.ExternalAuthProviderConfig;
import dev.cloudeko.zenei.extension.external.web.client.ExternalProviderAccessToken;

public interface ExternalAuthProvider {
    ExternalUserProfile getExternalUserProfile(ExternalProviderAccessToken accessToken);

    ExternalAuthProviderConfig config();

    String getAuthorizationEndpoint();

    String getTokenEndpoint();

    String getBaseEndpoint();
}
