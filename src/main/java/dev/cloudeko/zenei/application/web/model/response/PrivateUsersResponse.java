package dev.cloudeko.zenei.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Schema(name = "Users", description = "Represents a list of users")
public class PrivateUsersResponse {

    @JsonProperty("users")
    @Schema(description = "A list of users")
    private List<PrivateUserResponse> users;
}
