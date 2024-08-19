package dev.cloudeko.zenei.application.web.resource.backend;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;

@AllArgsConstructor
@RolesAllowed("admin")
@Path("/admin/settings")
@Tag(name = "Admin Settings Service", description = "API for managing settings of the application")
public class AdminSettingsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSettings() {
        return "Settings";
    }
}
