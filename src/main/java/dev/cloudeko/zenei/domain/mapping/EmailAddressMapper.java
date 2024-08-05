package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.domain.model.email.ConfirmationToken;
import dev.cloudeko.zenei.domain.model.email.EmailAddress;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ConfirmationTokenEntity;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.EmailAddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class)
public interface EmailAddressMapper {

    List<EmailAddress> toDomainList(List<EmailAddressEntity> entities);

    EmailAddress toDomain(EmailAddressEntity entity);

    void updateDomainFromEntity(EmailAddressEntity entity, @MappingTarget EmailAddress domain);

    EmailAddressEntity toEntity(EmailAddress domain);
}
