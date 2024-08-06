package dev.cloudeko.zenei.domain.model.email;

import java.util.Optional;

public interface EmailAddressRepository {

    void createEmailAddress(EmailAddress emailAddress);

    void deleteEmailAddress(String emailAddress);

    Optional<EmailAddress> findByEmailAddress(String emailAddress);
}
