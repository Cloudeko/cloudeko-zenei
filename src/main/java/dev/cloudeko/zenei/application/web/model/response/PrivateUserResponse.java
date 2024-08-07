package dev.cloudeko.zenei.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.cloudeko.zenei.domain.model.user.User;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RegisterForReflection
@Schema(name = "User", description = "Represents a user of the application")
public class PrivateUserResponse {

    @Schema(description = "Unique identifier of the user")
    private String id;

    @Schema(description = "Username of the user")
    private String username;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Primary email of the user")
    private String primaryEmailAddress;

    @Schema(description = "URL of the user's profile picture")
    private String image;

    @Schema(description = "Whether the user is an admin")
    private Boolean admin;

    @Schema(description = "Whether the user has a password")
    private Boolean passwordEnabled;

    @Schema(description = "Timestamp when the user was created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the user was last updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    public PrivateUserResponse(User user) {
        this.id = user.getId().toString();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.primaryEmailAddress = user.getPrimaryEmailAddress().getEmail();
        this.image = user.getImage();
        this.admin = user.isAdmin();
        this.passwordEnabled = user.isPasswordEnabled();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
