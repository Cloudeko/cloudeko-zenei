package dev.cloudeko.zenei.extension.core.feature.impl;

import dev.cloudeko.zenei.extension.core.config.ApplicationConfig;
import dev.cloudeko.zenei.extension.core.exception.EmailAlreadyExistsException;
import dev.cloudeko.zenei.extension.core.exception.UsernameAlreadyExistsException;
import dev.cloudeko.zenei.extension.core.feature.CreateUser;
import dev.cloudeko.zenei.extension.core.provider.HashProvider;
import dev.cloudeko.zenei.extension.core.provider.StringTokenProvider;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import dev.cloudeko.zenei.extension.core.model.user.CreateUserInput;
import dev.cloudeko.zenei.extension.core.model.user.User;
import dev.cloudeko.zenei.extension.core.model.user.UserPassword;
import dev.cloudeko.zenei.extension.core.repository.UserPasswordRepository;
import dev.cloudeko.zenei.extension.core.repository.UserRepository;
import dev.cloudeko.zenei.user.UserAccountManager;
import dev.cloudeko.zenei.user.runtime.BasicUserAccount;
import dev.cloudeko.zenei.user.runtime.BasicUserAccountManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@AllArgsConstructor
public class CreateUserImpl implements CreateUser {

    private final ApplicationConfig config;

    private final HashProvider hashProvider;
    private final StringTokenProvider stringTokenProvider;

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;

    private final BasicUserAccountManager userAccountManager;

    @Override
    @Transactional
    public User handle(CreateUserInput createUserInput) {
        final var emailAddress = EmailAddress.builder().email(createUserInput.getEmail()).emailVerified(true).build();
        if (!config.getAutoConfirm()) {
            final var token = stringTokenProvider.generateToken("mail", emailAddress.getEmail() + UUID.randomUUID());

            emailAddress.setEmailVerificationToken(token);
            emailAddress.setEmailVerificationTokenExpiresAt(LocalDateTime.now().plusDays(1));
            emailAddress.setEmailVerified(false);
        }

        final var user = User.builder()
                .username(createUserInput.getUsername())
                .primaryEmailAddress(emailAddress.getEmail())
                .emailAddresses(new ArrayList<>(List.of(emailAddress))).build();

        checkExistingUsername(user.getUsername());
        checkExistingEmail(user.getPrimaryEmailAddress().getEmail());

        userAccountManager.createUserBlocking(new BasicUserAccount(user.getUsername(), "test"));

        userRepository.createUser(user);

        if (createUserInput.isPasswordEnabled()) {
            final var userPassword = UserPassword.builder()
                    .user(user)
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
