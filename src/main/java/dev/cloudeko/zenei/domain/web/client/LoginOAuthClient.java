package dev.cloudeko.zenei.domain.web.client;

import dev.cloudeko.zenei.domain.model.external.AccessToken;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

@Path("/")
public interface LoginOAuthClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = MediaType.APPLICATION_JSON)
    AccessToken getAccessToken(@QueryParam("client_id") String clientId,
            @QueryParam("client_secret") String clientSecret,
            @QueryParam("code") String code,
            @QueryParam("redirect_uri") String redirectUri);
}
