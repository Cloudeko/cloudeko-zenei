package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.BasicUserAccount;
import io.vertx.sqlclient.Row;

public class DefaultUserAccount extends BasicUserAccount<Long> {

    public DefaultUserAccount() {
    }

    public DefaultUserAccount(String username) {
        super(username);
    }

    public DefaultUserAccount(Row row) {
        super(row.getLong("id"), row.getLocalDateTime("created_at"), row.getLocalDateTime("updated_at"));

        this.username = row.getString("username");
    }
}
