package dev.cloudeko.zenei.user;

public interface UserManager<ID> extends UserAccountRegistrationProvider<ID>, UserAccountProvider<ID>, UserQueryProvider<ID> {
}
