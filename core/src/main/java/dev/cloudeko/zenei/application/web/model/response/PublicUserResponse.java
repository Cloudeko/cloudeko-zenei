package dev.cloudeko.zenei.application.web.model.response;

import dev.cloudeko.zenei.domain.model.user.User;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@RegisterForReflection
@Schema(name = "User", description = "Represents a user of the application")
public class PublicUserResponse {

    @Schema(description = "Name of the user")
    private String username;

    @Schema(description = "URL of the user's profile picture")
    private String image;

    public PublicUserResponse(User user) {
        this.username = user.getUsername();
        this.image = user.getImage();
    }
}
