package dev.cloudeko.zenei.extension.external.web.external.google;

import dev.cloudeko.zenei.extension.external.web.external.BaseExternalClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;

@Path("/")
public interface GoogleClient extends BaseExternalClient<GoogleUser> {

    @GET
    @Path("/userinfo/v2/me")
    GoogleUser getCurrentlyLoggedInUser(@HeaderParam("Authorization") String token);

}
