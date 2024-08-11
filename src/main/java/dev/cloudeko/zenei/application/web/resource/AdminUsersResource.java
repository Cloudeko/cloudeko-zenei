package dev.cloudeko.zenei.application.web.resource;

import dev.cloudeko.zenei.application.web.model.response.PrivateUserResponse;
import dev.cloudeko.zenei.domain.feature.ListUsers;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import java.util.stream.Collectors;

@AllArgsConstructor
@RolesAllowed("admin")
@Path("/admin/users")
@Tag(name = "Admin Users Service", description = "API for managing users")
public class AdminUsersResource {

    private final ListUsers listUsers;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        final var users = listUsers.listUsers(page, size);
        final var usersResponse = users.stream().map(PrivateUserResponse::new).toList();

        return Response.ok(usersResponse).build();
    }
}
