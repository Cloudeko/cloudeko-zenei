package dev.cloudeko.zenei.extension.external.web.external.discord;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;

@Path("/")
public interface DiscordApiClient {

    @GET
    @Path("/users/@me")
    DiscordUser getCurrentlyLoggedInUser(@HeaderParam("Authorization") String token);
}
