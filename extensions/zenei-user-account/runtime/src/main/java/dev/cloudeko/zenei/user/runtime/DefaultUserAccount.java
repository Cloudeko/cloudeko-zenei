package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.BasicUserAccount;
import dev.cloudeko.zenei.user.EmailAddress;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.RowSet;

import java.util.ArrayList;
import java.util.List;

public class DefaultUserAccount extends BasicUserAccount<Long, DefaultEmailAddress, DefaultPhoneNumber> {

    public DefaultUserAccount() {
    }

    public DefaultUserAccount(String username) {
        super(username);
    }

    public static List<DefaultUserAccount> of(RowSet<Row> rows) {
        if (rows.size() == 0) {
            return List.of();
        }

        RowIterator<Row> iterator = rows.iterator();
        List<DefaultUserAccount> users = new ArrayList<>(rows.size());

        while (iterator.hasNext()) {
            users.add(of(iterator.next()));
        }

        return List.copyOf(users);
    }

    public static DefaultUserAccount of(Row row) {
        DefaultUserAccount user = new DefaultUserAccount();

        user.setId(row.getLong("id"));
        user.setUsername(row.getString("username"));
        user.setCreatedAt(row.getLocalDateTime("created_at"));
        user.setUpdatedAt(row.getLocalDateTime("updated_at"));

        return user;
    }
}
