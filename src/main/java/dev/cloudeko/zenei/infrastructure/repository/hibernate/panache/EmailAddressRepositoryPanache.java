package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.model.email.EmailAddress;
import dev.cloudeko.zenei.domain.model.email.EmailAddressRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.EmailAddressEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailAddressRepositoryPanache extends AbstractPanacheRepository<EmailAddressEntity> implements
        EmailAddressRepository {

    @Override
    public EmailAddress createEmailAddress(String emailAddress, String token) {
        return null;
    }

    @Override
    public void deleteEmailAddress(String emailAddress) {

    }

    @Override
    public EmailAddress findByEmailAddress(String emailAddress) {
        return null;
    }
}
