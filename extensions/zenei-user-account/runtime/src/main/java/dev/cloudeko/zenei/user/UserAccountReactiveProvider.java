package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface UserAccountReactiveProvider<ID> {

    Uni<UserAccount<ID>> findUserByIdentifier(ID id);

    default Optional<UserAccount<ID>> findUserByIdentifierBlocking(ID id) {
        return Optional.ofNullable(findUserByIdentifier(id).await().indefinitely());
    }

    Uni<UserAccount<ID>> findUserByPrimaryEmailAddress(String email);

    default Optional<UserAccount<ID>> findUserByPrimaryEmailAddressBlocking(String email) {
        return Optional.ofNullable(findUserByPrimaryEmailAddress(email).await().indefinitely());
    }

    Uni<UserAccount<ID>> findUserByPrimaryPhoneNumber(String phoneNumber);

    default Optional<UserAccount<ID>> findUserByPrimaryPhoneNumberBlocking(String phoneNumber) {
        return Optional.ofNullable(findUserByPrimaryPhoneNumber(phoneNumber).await().indefinitely());
    }

    Uni<UserAccount<ID>> findUserByUsername(String username);

    default Optional<UserAccount<ID>> findUserByUsernameBlocking(String username) {
        return Optional.ofNullable(findUserByUsername(username).await().indefinitely());
    }

    Uni<List<UserAccount<ID>>> listUsers();

    default List<UserAccount<ID>> listUsersBlocking() {
        return listUsers().await().indefinitely();
    }

    Uni<List<UserAccount<ID>>> listUsers(int page, int pageSize);

    default List<UserAccount<ID>> listUsersBlocking(int page, int pageSize) {
        return listUsers(page, pageSize).await().indefinitely();
    }

    Uni<UserAccount<ID>> createUser(UserAccount<ID> entity);

    default UserAccount<ID> createUserBlocking(UserAccount<ID> entity) {
        return createUser(entity).await().indefinitely();
    }

    Uni<UserAccount<ID>> updateUser(UserAccount<ID> entity);

    default UserAccount<ID> updateUserBlocking(UserAccount<ID> entity) {
        return updateUser(entity).await().indefinitely();
    }

    Uni<Boolean> deleteUser(ID id);

    default boolean deleteUserBlocking(ID id) {
        return deleteUser(id).await().indefinitely();
    }
}
