package dev.cloudeko.zenei.application.web.resource;

import dev.cloudeko.zenei.application.web.model.response.TokenResponse;
import dev.cloudeko.zenei.domain.feature.LoginUserWithAuthorizationCode;
import dev.cloudeko.zenei.infrastructure.config.ApplicationConfig;
import dev.cloudeko.zenei.infrastructure.config.ExternalAuthProviderConfig;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/oauth")
@AllArgsConstructor
@Tag(name = "OAuth Service", description = "OAuth service used for authentication")
public class OAuthResource {

    private final ApplicationConfig config;

    private final LoginUserWithAuthorizationCode loginUserWithAuthorizationCode;

    @GET
    @Path("/login/{provider}")
    public Response login(@PathParam("provider") String provider) {
        final var providerConfig = getProviderConfig(provider);

        if (providerConfig == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var uriBuilder = UriBuilder.fromUri(providerConfig.authorizationUri())
                .queryParam("client_id", providerConfig.clientId())
                .queryParam("redirect_uri", providerConfig.redirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", providerConfig.scope());

        return Response.temporaryRedirect(uriBuilder.build()).build();
    }

    @GET
    @Transactional
    @Path("/callback/{provider}")
    public Response callback(@PathParam("provider") String provider,
            @QueryParam("code") String code,
            @QueryParam("state") String state) {
        final var providerConfig = getProviderConfig(provider);

        if (providerConfig == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var token = loginUserWithAuthorizationCode.handle(provider, code);

        return Response.ok(new TokenResponse(token)).build();
    }

    private ExternalAuthProviderConfig getProviderConfig(String provider) {
        return config.getExternalAuthProvidersConfig().providers().get(provider);
    }
}
