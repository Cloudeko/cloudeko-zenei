package dev.cloudeko.zenei.application.web.model.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RegisterForReflection
@Schema(name = "Users", description = "Represents a list of users")
public class PrivateUsersResponse extends ArrayList<PrivateUserResponse> {

    public PrivateUsersResponse(List<PrivateUserResponse> users) {
        super(users);
    }
}
