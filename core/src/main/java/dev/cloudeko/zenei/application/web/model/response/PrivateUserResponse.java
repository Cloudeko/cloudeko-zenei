package dev.cloudeko.zenei.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.cloudeko.zenei.extension.core.model.user.User;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@RegisterForReflection
@Schema(name = "User", description = "Represents a user of the application")
public class PrivateUserResponse {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the user")
    private String id;

    @JsonProperty("username")
    @Schema(description = "Username of the user")
    private String username;

    @JsonProperty("first_name")
    @Schema(description = "First name of the user")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "Last name of the user")
    private String lastName;

    @JsonProperty("primary_email_address")
    @Schema(description = "Primary email of the user")
    private String primaryEmailAddress;

    @JsonProperty("email_addresses")
    @Schema(description = "Email addresses of the user")
    private List<EmailAddressResponse> emailAddresses;

    @JsonProperty("image")
    @Schema(description = "URL of the user's profile picture")
    private String image;

    @JsonProperty("admin")
    @Schema(description = "Whether the user is an admin")
    private Boolean admin;

    @JsonProperty("password_enabled")
    @Schema(description = "Whether the user has a password")
    private Boolean passwordEnabled;

    @JsonProperty("created_at")
    @Schema(description = "Timestamp when the user was created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Timestamp when the user was last updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    public PrivateUserResponse(User user) {
        this.id = user.getId().toString();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.primaryEmailAddress = user.getPrimaryEmailAddress().getEmail();
        this.emailAddresses = user.getEmailAddresses().stream().map(EmailAddressResponse::new).toList();
        this.image = user.getImage();
        this.admin = user.isAdmin();
        this.passwordEnabled = user.isPasswordEnabled();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
