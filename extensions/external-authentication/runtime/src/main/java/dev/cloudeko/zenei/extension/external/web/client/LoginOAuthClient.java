package dev.cloudeko.zenei.extension.external.web.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

@Path("/")
public interface LoginOAuthClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ClientHeaderParam(name = "Accept", value = MediaType.APPLICATION_JSON)
    ExternalProviderAccessToken getAccessToken(@FormParam("grant_type") String grantType,
            @FormParam("client_id") String clientId,
            @FormParam("client_secret") String clientSecret,
            @FormParam("code") String code,
            @FormParam("redirect_uri") String redirectUri);
}
