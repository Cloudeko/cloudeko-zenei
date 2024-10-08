package dev.cloudeko.zenei.extension.core.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserInput {
    private String username;

    private String email;
    private boolean autoConfirmEmail;

    private boolean passwordEnabled;
    private String password;
}
