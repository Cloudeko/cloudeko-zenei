package dev.cloudeko.zenei.extension.core.feature.impl;

import dev.cloudeko.zenei.extension.core.exception.UserNotFoundException;
import dev.cloudeko.zenei.extension.core.feature.FindUserByIdentifier;
import dev.cloudeko.zenei.extension.core.model.user.User;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class FindUserByIdentifierImpl implements FindUserByIdentifier {

    private final UserRepository userRepository;

    @Override
    public User handle(long identifier) {
        return userRepository.getUserById(identifier).orElseThrow(UserNotFoundException::new);
    }
}
