package dev.cloudeko.zenei.user;

public interface UserAccountRegistrationProvider<ID> {

    UserAccount<ID> createUser(UserAccount<ID> entity);

    UserAccount<ID> updateUser(UserAccount<ID> entity);

    boolean deleteUser(ID id);
}
