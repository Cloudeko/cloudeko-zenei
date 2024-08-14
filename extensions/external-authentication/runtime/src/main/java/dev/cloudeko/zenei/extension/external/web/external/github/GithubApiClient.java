package dev.cloudeko.zenei.extension.external.web.external.github;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/")
public interface GithubApiClient {

    @GET
    @Path("/user")
    GithubUser getCurrentlyLoggedInUser(@HeaderParam("Authorization") String token);

    @GET
    @Path("/user/emails")
    List<GithubUserEmail> getUserEmails(@HeaderParam("Authorization") String token);
}
