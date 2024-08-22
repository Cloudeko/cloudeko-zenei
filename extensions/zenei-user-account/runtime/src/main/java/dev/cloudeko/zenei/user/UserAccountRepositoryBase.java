package dev.cloudeko.zenei.user;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface UserAccountRepositoryBase<ENTITY, ID> {

    Uni<ENTITY> findUserByIdentifier(ID identifier);

    Uni<ENTITY> findUserByUsername(String username);

    Uni<List<ENTITY>> listUsers();

    Uni<List<ENTITY>> listUsers(int page, int pageSize);

    Uni<ENTITY> createUser(ENTITY entity);

    Uni<ENTITY> updateUser(ENTITY entity);

    Uni<Boolean> deleteUser(ENTITY entity);
}
