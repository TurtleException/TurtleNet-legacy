package de.turtle_exception.turtlenet.api;

import de.turtle_exception.turtlenet.api.exceptions.TurtleException;
import de.turtle_exception.turtlenet.core.TurtleClientImpl;
import de.turtle_exception.turtlenet.core.util.Checks;
import org.bukkit.configuration.MemorySection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * The TurtleClientBuilder is used to instantiate the {@link TurtleClient} interface, while avoiding an explicit
 * interaction with the {@code turtlenet.internal} package.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class TurtleClientBuilder {
    private Logger logger;

    // CONFIGURATION
    private String[] network;
    private Integer port;
    private Boolean listenIfSingle;
    private Boolean shareCredentials;
    private Boolean offerResources;
    private Configuration.MySQLConfig mySQLConfig;

    private File dataFolder;
    private File jarFile;

    public TurtleClientBuilder() { }

    public TurtleClientBuilder(@NotNull MemorySection config) {
        this.network          = config.getObject("network", String[].class);
        this.port             = config.getObject("port", Integer.class);
        this.listenIfSingle   = config.getObject("listenIfSingle", Boolean.class);
        this.shareCredentials = config.getObject("shareCredentials", Boolean.class);
        this.offerResources   = config.getObject("offerResources", Boolean.class);

        // MySQL
        boolean mySQL_enabled  = config.getBoolean("database.mysql.enable");
        String  mySQL_host     = config.getObject("database.mysql.credentials.host", String.class);
        Integer mySQL_port     = config.getObject("database.mysql.credentials.port", Integer.class);
        String  mySQL_database = config.getObject("database.mysql.credentials.database", String.class);
        String  mySQL_login    = config.getObject("database.mysql.credentials.login", String.class);
        String  mySQL_pass     = config.getObject("database.mysql.credentials.pass", String.class);
        this.setMySQLConfig(mySQL_enabled, mySQL_host, mySQL_port, mySQL_database, mySQL_login, mySQL_pass);
    }

    public @NotNull TurtleClient build() throws IllegalArgumentException, TurtleException {
        try {
            Checks.nonNull(logger, "Logger");
            Checks.nonNull(network, "Network configuration");
            Checks.nonNull(port, "Port");
            Checks.nonNull(listenIfSingle, "Flag 'listenIfSingle'");
            Checks.nonNull(shareCredentials, "Flag 'shareCredentials'");
            Checks.nonNull(offerResources, "Flag 'offerResources'");
            Checks.nonNull(mySQLConfig, "MySQL configuration");
            Checks.nonNull(dataFolder, "DataFolder");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }

        Configuration config = new Configuration(network, port, listenIfSingle, shareCredentials, offerResources, mySQLConfig);

        if (jarFile == null) {
            try {
                jarFile = new File(TurtleClient.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            } catch (URISyntaxException e) {
                throw new TurtleException("Could not imply jarFile", e);
            }
        }

        TurtleClientImpl client;
        try {
            client = new TurtleClientImpl(logger, config, dataFolder, jarFile);
        } catch (Exception e) { // TODO: replace with explicit exceptions (?)
            throw new TurtleException(e);
        }

        return client;
    }

    public static @NotNull TurtleClientBuilder createDefault(@NotNull Logger logger) {
        return new TurtleClientBuilder()
                .applyDefaults(false)
                .setLogger(logger);
    }

    public TurtleClientBuilder applyDefaults(boolean overwrite) {
        if (overwrite || network == null)
            this.network = new String[0];
        if (overwrite || port == null)
            this.port = 8346;
        if (overwrite || listenIfSingle == null)
            listenIfSingle = false;
        if (overwrite || shareCredentials == null)
            shareCredentials = true;
        if (overwrite || offerResources == null)
            offerResources = true;
        if (overwrite || mySQLConfig == null)
            this.setEmptyMySQLConfig();
        return this;
    }

    /* - - - */

    public TurtleClientBuilder setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public Logger getLogger() {
        return logger;
    }

    public TurtleClientBuilder setNetwork(String... network) {
        this.network = network;
        return this;
    }

    public String[] getNetwork() {
        return network;
    }

    public TurtleClientBuilder setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public TurtleClientBuilder setListenIfSingle(Boolean listenIfSingle) {
        this.listenIfSingle = listenIfSingle;
        return this;
    }

    public Boolean getListenIfSingle() {
        return listenIfSingle;
    }

    public TurtleClientBuilder setShareCredentials(Boolean shareCredentials) {
        this.shareCredentials = shareCredentials;
        return this;
    }

    public Boolean getShareCredentials() {
        return shareCredentials;
    }

    public TurtleClientBuilder setOfferResources(Boolean offerResources) {
        this.offerResources = offerResources;
        return this;
    }

    public Boolean getOfferResources() {
        return offerResources;
    }

    public TurtleClientBuilder setMySQLConfig(Configuration.MySQLConfig mySQLConfig) {
        this.mySQLConfig = mySQLConfig;
        return this;
    }

    public TurtleClientBuilder setMySQLConfig(boolean enabled, String host, Integer port, String database, String login, String pass) {
        return this.setMySQLConfig(new Configuration.MySQLConfig(enabled, host, port, database, login, pass));
    }

    public TurtleClientBuilder setEmptyMySQLConfig() {
        return this.setMySQLConfig(false, null, null, null, null, null);
    }

    public Configuration.MySQLConfig getMySQLConfig() {
        return mySQLConfig;
    }

    public TurtleClientBuilder setDataFolder(File dataFolder) {
        this.dataFolder = dataFolder;
        return this;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public TurtleClientBuilder setJarFile(File jarFile) {
        this.jarFile = jarFile;
        return this;
    }

    public File getJarFile() {
        return jarFile;
    }
}
