package dev.cloudeko.zenei.rest;

import dev.cloudeko.zenei.rest.request.SignupRequest;
import dev.cloudeko.zenei.service.AuthenticationService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth")
@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
@Tag(name = "Authentication Service", description = "API for user authentication")
public class AuthenticationResource {

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/signup")
    public Uni<Response> signup(@Valid SignupRequest request) {
        return Uni.createFrom().item(Response.ok().build());
    }

    @POST
    @Path("/token?grant_type=password")
    public Uni<Response> tokenWithPassword(@QueryParam("username") String username, @QueryParam("password") String password) {
        return Uni.createFrom().item(Response.ok().build());
    }

    @POST
    @Path("/token?grant_type=refresh_token")
    public Uni<Response> tokenWithRefreshToken(@QueryParam("refresh_token") String refreshToken) {
        return Uni.createFrom().item(Response.ok().build());
    }
}
