package dev.cloudeko.zenei.rest;

import dev.cloudeko.zenei.rest.request.SignupRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthResource {

    @Path("/signup")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<Response> signup(@Valid SignupRequest request) {
        return Uni.createFrom().item(Response.ok().build());
    }
}
