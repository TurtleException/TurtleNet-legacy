package de.turtle_exception.turtlenet.api;

import org.jetbrains.annotations.NotNull;

public record Configuration(
        @NotNull String[] network,
        int port,
        boolean listenIfSingle,
        boolean shareCredentials,
        boolean offerResources,
        @NotNull MySQLConfig mySQLConfig
) {
    // TODO: Should this be an ephemeral resource? This could help with load distribution
    public record MySQLConfig(
            boolean enabled,
            String host,
            Integer port,
            String database,
            String login,
            String pass
    ) { }
}
