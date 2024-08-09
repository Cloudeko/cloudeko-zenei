package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.UserNotFoundException;
import dev.cloudeko.zenei.domain.feature.FindUserByIdentifier;
import dev.cloudeko.zenei.domain.model.user.User;
import dev.cloudeko.zenei.domain.model.user.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class FindUserByIdentifierImpl implements FindUserByIdentifier {

    private final UserRepository userRepository;

    @Override
    public User handle(Long identifier) {
        return userRepository.getUserById(identifier).orElseThrow(UserNotFoundException::new);
    }
}
