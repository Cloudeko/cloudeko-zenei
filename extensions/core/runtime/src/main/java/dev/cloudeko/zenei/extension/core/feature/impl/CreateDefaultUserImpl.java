package dev.cloudeko.zenei.extension.core.feature.impl;

import dev.cloudeko.zenei.extension.core.config.DefaultUserConfig;
import dev.cloudeko.zenei.extension.core.feature.CreateDefaultUser;
import dev.cloudeko.zenei.extension.core.provider.HashProvider;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import dev.cloudeko.zenei.extension.core.model.user.User;
import dev.cloudeko.zenei.extension.core.model.user.UserPassword;
import dev.cloudeko.zenei.extension.core.repository.UserPasswordRepository;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class CreateDefaultUserImpl implements CreateDefaultUser {

    private final HashProvider hashProvider;

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;

    @Override
    public void handle(DefaultUserConfig config) {
        if (userRepository.existsByEmail(config.email())) {
            return;
        }

        if (userRepository.existsByUsername(config.username())) {
            return;
        }

        final var emailAddress = EmailAddress.builder().email(config.email()).emailVerified(true).build();
        final var user = User.builder()
                .username(config.username())
                .emailAddresses(new ArrayList<>(List.of(emailAddress)))
                .primaryEmailAddress(config.email())
                .admin(config.role().map(role -> role.equals("admin")).orElse(false))
                .build();

        userRepository.createUser(user);

        final var userPassword = UserPassword.builder()
                .user(user)
                .passwordHash(hashProvider.hashPassword(config.password()))
                .build();

        userPasswordRepository.createUserPassword(userPassword);
    }
}
