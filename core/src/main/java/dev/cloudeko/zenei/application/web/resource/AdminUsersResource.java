package dev.cloudeko.zenei.application.web.resource;

import dev.cloudeko.zenei.application.web.model.response.PrivateUserResponse;
import dev.cloudeko.zenei.application.web.model.response.PrivateUsersResponse;
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
import java.util.Optional;

@AllArgsConstructor
@RolesAllowed("admin")
@Path("/admin/users")
@Tag(name = "Admin Users Service", description = "API for managing users")
public class AdminUsersResource {

    private final ListUsers listUsers;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("page") Optional<Integer> page, @QueryParam("size") Optional<Integer> size) {
        final var users = listUsers.listUsers(page.orElse(0), size.orElse(100));
        final var usersResponse = users.stream().map(PrivateUserResponse::new).toList();

        return Response.ok(new PrivateUsersResponse(usersResponse)).build();
    }
}
