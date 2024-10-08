package dev.cloudeko.zenei.extension.jdbc.panache.mapping;

import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import dev.cloudeko.zenei.extension.jdbc.panache.entity.EmailAddressEntity;
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
