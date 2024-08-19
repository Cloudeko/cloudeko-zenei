package dev.cloudeko.zenei.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RegisterForReflection
@Schema(name = "EmailAddress", description = "Represents an email address")
public class EmailAddressResponse {

    @JsonProperty("email")
    @Schema(description = "Email address")
    private String email;

    @JsonProperty("email_verified")
    @Schema(description = "Whether the email address has been verified")
    private Boolean emailVerified;

    @JsonProperty("created_at")
    @Schema(description = "Timestamp when the email address was created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Schema(description = "Timestamp when the email address was last updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    public EmailAddressResponse(EmailAddress emailAddress) {
        this.email = emailAddress.getEmail();
        this.emailVerified = emailAddress.getEmailVerified();
        this.createdAt = emailAddress.getCreatedAt();
        this.updatedAt = emailAddress.getUpdatedAt();
    }
}
