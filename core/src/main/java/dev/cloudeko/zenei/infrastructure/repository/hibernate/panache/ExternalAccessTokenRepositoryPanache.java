package dev.cloudeko.zenei.infrastructure.repository.hibernate.panache;

import dev.cloudeko.zenei.domain.mapping.ExternalAccessTokenMapper;
import dev.cloudeko.zenei.extension.core.model.account.ExternalAccessToken;
import dev.cloudeko.zenei.extension.core.repository.ExternalAccessTokenRepository;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ExternalAccessTokenEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.List;

import static io.quarkus.panache.common.Parameters.with;

@ApplicationScoped
@AllArgsConstructor
public class ExternalAccessTokenRepositoryPanache extends AbstractPanacheRepository<ExternalAccessTokenEntity> implements
        ExternalAccessTokenRepository {

    private final ExternalAccessTokenMapper externalAccessTokenMapper;

    @Override
    public List<ExternalAccessToken> listByProvider(long user, String provider) {
        final var tokens = list("#ExternalAccessTokenEntity.listByProvider", with("user", user).and("provider", provider));

        return externalAccessTokenMapper.toDomainList(tokens);
    }
}
