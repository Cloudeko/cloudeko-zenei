package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.user.CreateUserInput;
import dev.cloudeko.zenei.domain.model.user.User;

public interface CreateUser {

    User handle(CreateUserInput input);
}
