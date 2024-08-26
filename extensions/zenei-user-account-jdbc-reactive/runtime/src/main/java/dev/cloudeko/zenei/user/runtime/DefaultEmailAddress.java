package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.EmailAddress;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.RowSet;

import java.util.ArrayList;
import java.util.List;

public class DefaultEmailAddress extends EmailAddress {
    public DefaultEmailAddress() {
    }

    public DefaultEmailAddress(String email, boolean verified, boolean primary) {
        super(email, verified, primary);
    }

    public static List<DefaultEmailAddress> of(RowSet<Row> rows) {
        if (rows.size() == 0) {
            return List.of();
        }

        RowIterator<Row> iterator = rows.iterator();
        List<DefaultEmailAddress> emails = new ArrayList<>(rows.size());

        while (iterator.hasNext()) {
            emails.add(of(iterator.next()));
        }

        return List.copyOf(emails);
    }

    public static DefaultEmailAddress of(Row row) {
        DefaultEmailAddress email = new DefaultEmailAddress();

        email.setEmail(row.getString("email"));
        email.setPrimaryEmail(row.getBoolean("primary_email"));
        email.setEmailVerified(row.getBoolean("email_verified"));
        email.setCreatedAt(row.getLocalDateTime("created_at"));
        email.setUpdatedAt(row.getLocalDateTime("updated_at"));

        return email;
    }
}
