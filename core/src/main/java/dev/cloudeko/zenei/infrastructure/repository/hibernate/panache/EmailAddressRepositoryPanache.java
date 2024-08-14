package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.exception.InvalidConfirmationTokenException;
import dev.cloudeko.zenei.domain.exception.UserNotFoundException;
import dev.cloudeko.zenei.domain.mapping.EmailAddressMapper;
import dev.cloudeko.zenei.domain.model.email.EmailAddress;
import dev.cloudeko.zenei.domain.model.email.EmailAddressRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.EmailAddressEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
public class EmailAddressRepositoryPanache extends AbstractPanacheRepository<EmailAddressEntity> implements
        EmailAddressRepository {

    private final EmailAddressMapper emailAddressMapper;

    @Override
    public void createEmailAddress(EmailAddress emailAddress) {
        final var user = findUserEntityByEmail(emailAddress.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        final var emailAddressEntity = emailAddressMapper.toEntity(emailAddress);
        persist(emailAddressEntity);

        emailAddressMapper.updateDomainFromEntity(emailAddressEntity, emailAddress);
    }

    @Override
    public void deleteEmailAddress(String emailAddress) {
        delete("email", emailAddress);
    }

    @Override
    public Optional<EmailAddress> findByEmailAddress(String emailAddress) {
        final var emailAddressEntity = find("email", emailAddress).firstResult();

        if (emailAddressEntity == null) {
            return Optional.empty();
        }

        return Optional.of(emailAddressMapper.toDomain(emailAddressEntity));
    }

    @Override
    public void confirmEmailAddress(String token) {
        final var updated = update("#EmailAddressEntity.confirmEmail", Parameters.with("token", token));
        if (updated == 0) {
            throw new InvalidConfirmationTokenException();
        }
    }
}
