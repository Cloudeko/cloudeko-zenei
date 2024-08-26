package dev.cloudeko.zenei.user;

import java.util.List;

public interface UserAccountListingProvider<ID> {
    
    long countUsers();

    List<UserAccount<ID>> listUsers();

    List<UserAccount<ID>> listUsers(int page, int pageSize);

    List<UserAccount<ID>> listUsers(int page, int pageSize, String sortField, boolean ascending);
}
