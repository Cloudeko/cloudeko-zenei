package dev.cloudeko.zenei.domain.model.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginPasswordInput {
    private String email;
    private String password;
}
