package dev.cloudeko.zenei.user.deployment;

public interface TableSchema {
    String getClient();

    String getDdl();

    boolean isSupportsIfNotExists();
}
