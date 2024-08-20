package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.user.CreateUserInput;
import dev.cloudeko.zenei.extension.core.model.user.User;

public interface CreateUser {

    User handle(CreateUserInput input);
}
