package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;

public interface UserManager<ID> extends UserAccountProvider<ID>, UserAccountReactiveProvider<ID> {

    @Override
    default Optional<UserAccount<ID>> findUserByIdentifierBlocking(ID id) {
        return Optional.ofNullable(findUserByIdentifier(id).await().indefinitely());
    }

    @Override
    default Optional<UserAccount<ID>> findUserByUsernameBlocking(String username) {
        return Optional.ofNullable(findUserByUsername(username).await().indefinitely());
    }

    @Override
    default List<UserAccount<ID>> listUsersBlocking() {
        return listUsers().await().indefinitely();
    }

    @Override
    default List<UserAccount<ID>> listUsersBlocking(int page, int pageSize) {
        return listUsers(page, pageSize).await().indefinitely();
    }

    @Override
    default UserAccount<ID> createUserBlocking(UserAccount<ID> entity) {
        return createUser(entity).await().indefinitely();
    }

    @Override
    default UserAccount<ID> updateUserBlocking(UserAccount<ID> entity) {
        return updateUser(entity).await().indefinitely();
    }

    @Override
    default boolean deleteUserBlocking(ID id) {
        return deleteUser(id).await().indefinitely();
    }
}
