package dev.cloudeko.zenei.domain.model.email;

public interface EmailAddressRepository {

    EmailAddress createEmailAddress(String emailAddress, String token);

    void deleteEmailAddress(String emailAddress);

    EmailAddress findByEmailAddress(String emailAddress);
}
