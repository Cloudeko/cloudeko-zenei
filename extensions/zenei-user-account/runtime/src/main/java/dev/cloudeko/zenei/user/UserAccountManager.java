package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface UserAccountManager<ENTITY, ID> {

    Uni<ENTITY> findUserByIdentifier(ID id);

    default Optional<ENTITY> findUserByIdentifierBlocking(ID id) {
        return Optional.ofNullable(findUserByIdentifier(id).await().indefinitely());
    }

    Uni<ENTITY> findUserByPrimaryEmailAddress(String email);

    default Optional<ENTITY> findUserByPrimaryEmailAddressBlocking(String email) {
        return Optional.ofNullable(findUserByPrimaryEmailAddress(email).await().indefinitely());
    }

    Uni<ENTITY> findUserByPrimaryPhoneNumber(String phoneNumber);

    default Optional<ENTITY> findUserByPrimaryPhoneNumberBlocking(String phoneNumber) {
        return Optional.ofNullable(findUserByPrimaryPhoneNumber(phoneNumber).await().indefinitely());
    }

    Uni<ENTITY> findUserByUsername(String username);

    default Optional<ENTITY> findUserByUsernameBlocking(String username) {
        return Optional.ofNullable(findUserByUsername(username).await().indefinitely());
    }

    Uni<List<ENTITY>> listUsers();

    default List<ENTITY> listUsersBlocking() {
        return listUsers().await().indefinitely();
    }

    Uni<List<ENTITY>> listUsers(int page, int pageSize);

    default List<ENTITY> listUsersBlocking(int page, int pageSize) {
        return listUsers(page, pageSize).await().indefinitely();
    }

    Uni<ENTITY> createUser(ENTITY entity);

    default ENTITY createUserBlocking(ENTITY entity) {
        return createUser(entity).await().indefinitely();
    }

    Uni<ENTITY> updateUser(ENTITY entity);

    default ENTITY updateUserBlocking(ENTITY entity) {
        return updateUser(entity).await().indefinitely();
    }

    Uni<Boolean> deleteUser(ID identifier);

    default boolean deleteUserBlocking(ID identifier) {
        return deleteUser(identifier).await().indefinitely();
    }
}
