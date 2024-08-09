package dev.cloudeko.zenei.infrastructure.web.security;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.TokenAuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import static io.vertx.ext.web.handler.impl.HTTPAuthorizationHandler.Type.BEARER;

public class AccessTokenSecurityAuthMechanism {}/*implements HttpAuthenticationMechanism {

    // The set of supported credential types
    static final Set<Class<? extends AuthenticationRequest>> credentialTypes = Set.of(TokenAuthenticationRequest.class);

    // The prefix of the Authorization header
    private static final String BEARER_PREFIX = BEARER + " " + AdminAccessTokenManager.TOKEN_PREFIX;

    // The lowercase version of the prefix of the Authorization header
    private static final String LOWERCASE_BASIC_PREFIX = BEARER_PREFIX.toLowerCase(Locale.ENGLISH);

    // The length of the prefix of the Authorization header
    private static final int PREFIX_LENGTH = BEARER_PREFIX.length();

    /**
     * Authenticates the user using the provided token in the Authorization header of the request.
     *
     * @param context The RoutingContext of the request.
     * @param identityProviderManager The IdentityProviderManager to authenticate the request.
     * @return A Uni of SecurityIdentity representing the authenticated user or a null item if authentication fails.
     */
    /*@Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        List<String> authHeaders = context.request().headers().getAll(HttpHeaderNames.AUTHORIZATION);

        // Try to extract the token from the Authorization header
        if (authHeaders != null) {
            for (String current : authHeaders) {
                if (current.toLowerCase(Locale.ENGLISH).startsWith(LOWERCASE_BASIC_PREFIX)) {

                    // Extract the token from the header
                    String tokenChallenge = current.substring(PREFIX_LENGTH);

                    // Create a token credentials
                    TokenCredential tokenCredential = new TokenCredential(tokenChallenge, "access_token");

                    // We have found a suitable header, so try to authenticate
                    return identityProviderManager.authenticate(new TokenAuthenticationRequest(tokenCredential));
                }
            }
        }

        // No suitable header has been found in this request,
        return Uni.createFrom().nullItem();
    }

    /**
     * Sends an authentication challenge, if required.
     *
     * @param context The RoutingContext of the request.
     * @return A Uni of Boolean that is always false, as Clerk does not require an explicit challenge.
     */
    /*@Override
    public Uni<Boolean> sendChallenge(RoutingContext context) {
        return Uni.createFrom().item(false);
    }

    /**
     * Retrieves the challenge data, if any.
     *
     * @param context The RoutingContext of the request.
     * @return A Uni of ChallengeData that is always a null item, as Clerk does not require any challenge data.
     */
    /*@Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        return Uni.createFrom().nullItem();
    }

    /**
     * Retrieves the set of credential types supported by this mechanism.
     *
     * @return A Set of classes representing the supported credential types.
     */
   /* @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return credentialTypes;
    }
}
*/
