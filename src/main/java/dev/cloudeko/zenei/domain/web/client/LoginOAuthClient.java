package dev.cloudeko.zenei.domain.web.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

@Path("/")
public interface LoginOAuthClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = "application/json")
    AccessToken getAccessToken(@QueryParam("client_id") String clientId,
            @QueryParam("client_secret") String clientSecret,
            @QueryParam("code") String code,
            @QueryParam("redirect_uri") String redirectUri);
}
