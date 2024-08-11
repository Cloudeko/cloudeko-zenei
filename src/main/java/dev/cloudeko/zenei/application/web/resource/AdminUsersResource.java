package dev.cloudeko.zenei.application.web.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;

@AllArgsConstructor
@RolesAllowed("admin")
@Path("/admin/users")
@Tag(name = "Admin Users Service", description = "API for managing users")
public class AdminUsersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        return Response.ok().build();
    }
}
