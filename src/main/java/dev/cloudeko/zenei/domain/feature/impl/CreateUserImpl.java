package dev.cloudeko.zenei.domain.feature.impl;

import dev.cloudeko.zenei.domain.exception.EmailAlreadyExistsException;
import dev.cloudeko.zenei.domain.exception.UsernameAlreadyExistsException;
import dev.cloudeko.zenei.domain.feature.CreateUser;
import dev.cloudeko.zenei.domain.model.user.*;
import dev.cloudeko.zenei.domain.provider.HashProvider;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateUserImpl implements CreateUser {

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;
    private final HashProvider hashProvider;

    @Override
    public User handle(CreateUserInput createUserInput) {
        final var user = User.builder().username(createUserInput.getUsername()).email(createUserInput.getEmail()).build();

        checkExistingUsername(user.getUsername());
        checkExistingEmail(user.getEmail());

        userRepository.createUser(user);

        if (createUserInput.isPasswordEnabled()) {
            final var userPassword = UserPassword.builder()
                    .user(user.getId())
                    .passwordHash(hashProvider.hashPassword(createUserInput.getPassword()))
                    .build();

            userPasswordRepository.createUserPassword(userPassword);
        }

        return user;
    }

    private void checkExistingUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }
    }

    private void checkExistingEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }
}
