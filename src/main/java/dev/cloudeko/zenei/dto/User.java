package dev.cloudeko.zenei.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", description = "Represents a user of the application")
public class User {

    @Schema(description = "Unique identifier of the user")
    private String uid;

    @Schema(description = "Name of the user")
    private String name;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Whether the user's email has been verified")
    private Boolean emailVerified;

    @Schema(description = "URL of the user's profile picture")
    private String image;

    @Schema(description = "Whether the user is an admin")
    private Boolean admin;

    @Schema(description = "Timestamp when the user was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the user was last updated")
    private LocalDateTime updatedAt;
}
