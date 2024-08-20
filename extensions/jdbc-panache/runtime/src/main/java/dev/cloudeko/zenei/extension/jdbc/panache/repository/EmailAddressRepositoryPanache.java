package dev.cloudeko.zenei.extension.jdbc.panache.repository;

import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import dev.cloudeko.zenei.extension.core.repository.EmailAddressRepository;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.EmailAddressEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.UserEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.mapping.EmailAddressMapper;
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
        Optional<UserEntity> user = findUserEntityByEmail(emailAddress.getEmail());
        if (user.isEmpty()) {
            return;
        }

        EmailAddressEntity emailAddressEntity = emailAddressMapper.toEntity(emailAddress);
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
    public boolean confirmEmailAddress(String token) {
        final var updated = update("#EmailAddressEntity.confirmEmail", Parameters.with("token", token));
        return updated != 0;
    }
}
