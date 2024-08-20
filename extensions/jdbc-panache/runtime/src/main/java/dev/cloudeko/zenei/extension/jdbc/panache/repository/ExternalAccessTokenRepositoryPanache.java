package dev.cloudeko.zenei.extension.jdbc.panache.repository;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccessToken;
import dev.cloudeko.zenei.extension.core.repository.ExternalAccessTokenRepository;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.ExternalAccessTokenEntity;
import dev.cloudeko.zenei.extension.jdbc.panache.mapping.ExternalAccessTokenMapper;
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
        List<ExternalAccessTokenEntity> tokens = list("#ExternalAccessTokenEntity.listByProvider",
                with("user", user).and("provider", provider));

        return externalAccessTokenMapper.toDomainList(tokens);
    }
}
