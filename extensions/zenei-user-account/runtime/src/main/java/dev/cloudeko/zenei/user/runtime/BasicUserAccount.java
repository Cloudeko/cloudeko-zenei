package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.UserAccount;
import io.vertx.sqlclient.Row;

public class BasicUserAccount extends UserAccount<Long> {

    public BasicUserAccount() {
        super();
    }

    public BasicUserAccount(String username, String image) {
        super(username, image);
    }

    public BasicUserAccount(Row row) {
        super(row.getLong("id"),
                row.getString("username"),
                row.getString("image"),
                row.getLocalDateTime("created_at"),
                row.getLocalDateTime("updated_at"));
    }
}
