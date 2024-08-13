package dev.cloudeko.zenei.domain.provider;

import dev.cloudeko.zenei.domain.model.external.AccessToken;
import dev.cloudeko.zenei.domain.model.external.ExternalUserProfile;

public interface ExternalAuthProvider {
    ExternalUserProfile getExternalUserProfile(AccessToken accessToken);
}
