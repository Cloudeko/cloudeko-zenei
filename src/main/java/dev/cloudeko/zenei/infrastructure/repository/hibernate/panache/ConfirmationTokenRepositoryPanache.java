package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.model.mail.ConfirmationTokenRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ConfirmationTokenEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfirmationTokenRepositoryPanache extends AbstractPanacheRepository<ConfirmationTokenEntity> implements
        ConfirmationTokenRepository {

}
