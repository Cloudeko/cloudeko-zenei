package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.feature.ListUsers;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class ListUsersImpl implements ListUsers {

    private final UserRepository userRepository;

    @Override
    public List<User> listUsers(int offset, int limit) {
        return userRepository.listUsers(offset, limit);
    }
}
