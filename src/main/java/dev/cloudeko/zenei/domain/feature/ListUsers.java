package dev.cloudeko.zenei.domain.feature;

import dev.cloudeko.zenei.domain.model.user.User;

import java.util.List;

public interface ListUsers {
    List<User> listUsers(int offset, int limit);
}
