package dev.cloudeko.zenei.application.web.model.request;

import dev.cloudeko.zenei.domain.model.user.CreateUserInput;

public record SignupRequest(String name, String email, String password) {

    public CreateUserInput toCreateUserInput() {
        return new CreateUserInput(name, email, true, password);
    }
}
