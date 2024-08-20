package dev.cloudeko.zenei.extension.core.feature.impl;

import dev.cloudeko.zenei.extension.core.config.ApplicationConfig;
import dev.cloudeko.zenei.extension.core.exception.EmailAlreadyExistsException;
import dev.cloudeko.zenei.extension.core.exception.UserNotFoundException;
import dev.cloudeko.zenei.extension.core.exception.UsernameAlreadyExistsException;
import dev.cloudeko.zenei.extension.core.feature.UserDataManager;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import dev.cloudeko.zenei.extension.core.model.user.CreateUserInput;
import dev.cloudeko.zenei.extension.core.model.user.User;
import dev.cloudeko.zenei.extension.core.model.user.UserPassword;
import dev.cloudeko.zenei.extension.core.provider.HashProvider;
import dev.cloudeko.zenei.extension.core.provider.StringTokenProvider;
import dev.cloudeko.zenei.extension.core.repository.UserPasswordRepository;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@AllArgsConstructor
public class DefaultUserDataManager implements UserDataManager {

    private final ApplicationConfig config;

    private final HashProvider hashProvider;
    private final StringTokenProvider stringTokenProvider;

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;

    @Override
    public User findUserByIdentifier(String identifier) {
        if (identifier == null) {
            throw new UserNotFoundException();
        }

        if (identifier.matches("\\d+")) {
            return userRepository.getUserById(Long.parseLong(identifier)).orElseThrow(UserNotFoundException::new);
        }

        return userRepository.getUserByUsername(identifier).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> listUsers(int offset, int limit) {
        return userRepository.listUsers(offset, limit);
    }

    @Override
    @Transactional
    public User createUser(CreateUserInput input) {
        final var emailAddress = EmailAddress.builder().email(input.getEmail()).emailVerified(true).build();
        if (!config.getAutoConfirm()) {
            final var token = stringTokenProvider.generateToken("mail", emailAddress.getEmail() + UUID.randomUUID());

            emailAddress.setEmailVerificationToken(token);
            emailAddress.setEmailVerificationTokenExpiresAt(LocalDateTime.now().plusDays(1));
            emailAddress.setEmailVerified(false);
        }

        final var user = User.builder()
                .username(input.getUsername())
                .primaryEmailAddress(emailAddress.getEmail())
                .emailAddresses(new ArrayList<>(List.of(emailAddress))).build();

        checkExistingUsername(user.getUsername());
        checkExistingEmail(user.getPrimaryEmailAddress().getEmail());

        userRepository.createUser(user);

        if (input.isPasswordEnabled()) {
            final var userPassword = UserPassword.builder()
                    .user(user)
                    .passwordHash(hashProvider.hashPassword(input.getPassword()))
                    .build();

            userPasswordRepository.createUserPassword(userPassword);
        }

        return user;
    }

    @Override
    public void updateUser(CreateUserInput input) {

    }

    @Override
    public void deleteUser(String identifier) {

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
