package dev.cloudeko.zenei.extension.core.repository;

import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;

import java.util.Optional;

public interface EmailAddressRepository {

    void createEmailAddress(EmailAddress emailAddress);

    void deleteEmailAddress(String emailAddress);

    Optional<EmailAddress> findByEmailAddress(String emailAddress);

    void confirmEmailAddress(String token);
}
