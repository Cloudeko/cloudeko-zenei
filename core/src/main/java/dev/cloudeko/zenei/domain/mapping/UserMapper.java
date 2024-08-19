package dev.cloudeko.zenei.domain.mapping;

import dev.cloudeko.zenei.extension.core.model.account.ExternalAccount;
import dev.cloudeko.zenei.extension.core.model.email.EmailAddress;
import dev.cloudeko.zenei.extension.core.model.user.User;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.ExternalAccountEntity;
import dev.cloudeko.zenei.infrastructure.repository.hibernate.entity.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, uses = { EmailAddressMapper.class, ExternalAccountMapper.class })
public interface UserMapper {

    List<User> toDomainList(List<UserEntity> entities);

    User toDomain(UserEntity entity);

    void updateDomainFromEntity(UserEntity entity, @MappingTarget User domain);

    List<UserEntity> toEntityList(List<User> domains);

    UserEntity toEntity(User domain);

    default String map(EmailAddress emailAddress) {
        return emailAddress.getEmail();
    }

    @AfterMapping
    default void setBackReferenceInAccounts(User user, @MappingTarget UserEntity entity) {
        if (user.getAccounts() != null) {
            entity.getAccounts().forEach(account -> account.setUser(entity));
        }
    }

    @AfterMapping
    default void setBackReferenceInEmailAddresses(User user, @MappingTarget UserEntity entity) {
        if (user.getEmailAddresses() != null) {
            entity.getEmailAddresses().forEach(emailAddress -> emailAddress.setUser(entity));
        }
    }
}
