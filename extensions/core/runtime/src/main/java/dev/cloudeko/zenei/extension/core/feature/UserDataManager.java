package dev.cloudeko.zenei.extension.core.feature;

import dev.cloudeko.zenei.extension.core.model.user.CreateUserInput;
import dev.cloudeko.zenei.extension.core.model.user.User;

import java.util.List;

public interface UserDataManager {

    User findUserByIdentifier(String identifier);

    List<User> listUsers(int offset, int limit);

    User createUser(CreateUserInput input);

    void updateUser(CreateUserInput input);

    void deleteUser(String identifier);
}
