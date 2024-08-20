package dev.cloudeko.zenei.extension.external.web.external.github;

import dev.cloudeko.zenei.extension.external.web.external.BaseExternalClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/")
public interface GithubClient extends BaseExternalClient<GithubUser> {

    @GET
    @Path("/user")
    GithubUser getCurrentlyLoggedInUser(@HeaderParam("Authorization") String token);

    @GET
    @Path("/user/emails")
    List<GithubUserEmail> getUserEmails(@HeaderParam("Authorization") String token);
}
