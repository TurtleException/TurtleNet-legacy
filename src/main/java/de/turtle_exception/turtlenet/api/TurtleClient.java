package de.turtle_exception.turtlenet.api;

import de.turtle_exception.fancyformat.FancyFormatter;
import de.turtle_exception.turtlenet.api.entities.Group;
import de.turtle_exception.turtlenet.api.entities.JsonResource;
import de.turtle_exception.turtlenet.api.entities.Turtle;
import de.turtle_exception.turtlenet.api.entities.User;
import de.turtle_exception.turtlenet.api.entities.attributes.TurtleContainer;
import de.turtle_exception.turtlenet.api.entities.chat.Endpoint;
import de.turtle_exception.turtlenet.api.entities.chat.MessageEntity;
import de.turtle_exception.turtlenet.api.entities.chat.SyncChannel;
import de.turtle_exception.turtlenet.api.entities.chat.endpoints.DiscordChannel;
import de.turtle_exception.turtlenet.api.entities.chat.endpoints.MinecraftServer;
import de.turtle_exception.turtlenet.api.entities.chat.endpoints.MinecraftWorld;
import de.turtle_exception.turtlenet.api.entities.discord.DiscordServer;
import de.turtle_exception.turtlenet.core.util.logging.NestedLogger;
import de.turtle_exception.turtlenet.core.util.logging.SimpleFormatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public interface TurtleClient extends TurtleContainer<Turtle> {
    /**
     * Returns the root logger of the API.
     * <p> Every {@link Logger} that is used by parts of the API is a {@link NestedLogger} that will stream its output
     * to this Logger.
     * @return API logger.
     * @see NestedLogger
     * @see SimpleFormatter
     */
    @NotNull Logger getLogger();

    /** Returns the version of this application as a String representation. */
    @NotNull String getVersion();

    /** Returns the version of this application as an array of integers, starting with the most major version. */
    int[] getVersions();

    /**
     * Return the {@link FancyFormatter} of this TurtleClient. This formatter is used globally. Any modifications to it
     * will impact formatting on every layer of the API.
     * @return FancyFormatter instance.
     */
    @NotNull FancyFormatter getFormatter();

    /* - - - */

    @Override
    @NotNull Set<Turtle> getTurtles();

    @Override
    @Nullable Turtle getTurtleById(long id);

    /**
     * Returns an immutable Set of all cached {@link Group} objects.
     * @return List of cached Groups.
     */
    default @NotNull Set<Group> getGroups() {
        return this.getTurtles(Group.class);
    }

    /**
     * Returns a single {@link Group} specified by its id, or {@code null} if no such object is stored in the underlying
     * cache.
     * @param id The unique id of the Group.
     * @return The requested Group (may be {@code null}).
     * @see Group#getId()
     */
    default @Nullable Group getGroupById(long id) {
        return this.getTurtleById(id, Group.class);
    }

    /**
     * Returns an immutable Set of all cached {@link JsonResource} objects.
     * @return List of cached JsonResources.
     */
    default @NotNull Set<JsonResource> getJsonResources() {
        return this.getTurtles(JsonResource.class);
    }

    /**
     * Returns a single {@link JsonResource} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the JsonResource.
     * @return The requested JsonResource (may be {@code null}).
     * @see JsonResource#getId()
     */
    default @Nullable JsonResource getJsonResourceById(long id) {
        return this.getTurtleById(id, JsonResource.class);
    }

    /**
     * Returns an immutable Set of all cached {@link User} objects.
     * @return List of cached Users.
     */
    default @NotNull Set<User> getUsers() {
        return this.getTurtles(User.class);
    }

    /**
     * Returns a single {@link User} specified by its id, or {@code null} if no such object is stored in the underlying
     * cache.
     * @param id The unique id of the User.
     * @return The requested User (may be {@code null}).
     * @see User#getId()
     */
    default @Nullable User getUserById(long id) {
        return this.getTurtleById(id, User.class);
    }

    // CHAT

    /**
     * Returns an immutable Set of all cached {@link DiscordChannel} objects.
     * @return List of cached DiscordChannel endpoints.
     */
    default @NotNull Set<DiscordChannel> getDiscordChannelEndpoints() {
        return this.getTurtles(DiscordChannel.class);
    }

    /**
     * Returns a single {@link DiscordChannel} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the DiscordChannel.
     * @return The requested DiscordChannel (may be {@code null}).
     * @see DiscordChannel#getId()
     */
    default @Nullable DiscordChannel getDiscordChannelEndpointById(long id) {
        return this.getTurtleById(id, DiscordChannel.class);
    }

    /**
     * Returns an immutable Set of all cached {@link MinecraftServer} objects.
     * @return List of cached MinecraftServer endpoints.
     */
    default @NotNull Set<MinecraftServer> getMinecraftServerEndpoints() {
        return this.getTurtles(MinecraftServer.class);
    }

    /**
     * Returns a single {@link MinecraftServer} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the MinecraftServer.
     * @return The requested MinecraftServer (may be {@code null}).
     * @see MinecraftServer#getId()
     */
    default @Nullable MinecraftServer getMinecraftServerEndpointById(long id) {
        return this.getTurtleById(id, MinecraftServer.class);
    }

    /**
     * Returns an immutable Set of all cached {@link MinecraftWorld} objects.
     * @return List of cached MinecraftWorld endpoints.
     */
    default @NotNull Set<MinecraftWorld> getMinecraftWorldEndpoints() {
        return this.getTurtles(MinecraftWorld.class);
    }

    /**
     * Returns a single {@link MinecraftWorld} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the MinecraftWorld.
     * @return The requested MinecraftWorld (may be {@code null}).
     * @see MinecraftWorld#getId()
     */
    default @Nullable MinecraftWorld getMinecraftWorldEndpointById(long id) {
        return this.getTurtleById(id, MinecraftWorld.class);
    }

    /**
     * Returns an immutable Set of all cached {@link Endpoint} objects.
     * @return List of cached Endpoint.
     */
    default @NotNull Set<Endpoint> getEndpoints() {
        return this.getTurtles(Endpoint.class);
    }

    /**
     * Returns a single {@link Endpoint} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the Endpoint.
     * @return The requested Endpoint (may be {@code null}).
     * @see Endpoint#getId()
     */
    default @Nullable Endpoint getEndpointById(long id) {
        return this.getTurtleById(id, Endpoint.class);
    }

    /**
     * Returns an immutable Set of all cached {@link MessageEntity} objects.
     * <p> To only get messages of a specific type, use {@link TurtleClient#getTurtles(Class)}.
     * @return List of cached messages.
     */
    default @NotNull Set<MessageEntity> getMessages() {
        return this.getTurtles(MessageEntity.class);
    }

    /**
     * Returns a single {@link MessageEntity} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the message.
     * @return The requested message (may be {@code null}).
     * @see MessageEntity#getId()
     */
    default @Nullable MessageEntity getMessageById(long id) {
        return this.getTurtleById(id, MessageEntity.class);
    }

    /**
     * Returns an immutable Set of all cached {@link SyncChannel} objects.
     * @return List of cached channels.
     */
    default @NotNull Set<SyncChannel> getChannels() {
        return this.getTurtles(SyncChannel.class);
    }

    /**
     * Returns a single {@link SyncChannel} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the channel.
     * @return The requested channel (may be {@code null}).
     * @see SyncChannel#getId()
     */
    default @Nullable SyncChannel getChannelById(long id) {
        return this.getTurtleById(id, SyncChannel.class);
    }

    // DISCORD

    /**
     * Returns an immutable Set of all cached {@link DiscordServer} objects.
     * @return List of cached DiscordServers.
     */
    default @NotNull Set<DiscordServer> getDiscordServers() {
        return this.getTurtles(DiscordServer.class);
    }

    /**
     * Returns a single {@link DiscordServer} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the DiscordServer.
     * @return The requested DiscordServer (may be {@code null}).
     * @see DiscordServer#getId()
     */
    default @Nullable DiscordServer getDiscordServerId(long id) {
        return this.getTurtleById(id, DiscordServer.class);
    }

    // MINECRAFT

    /**
     * Returns an immutable Set of all cached {@link MinecraftServer} objects.
     * @return List of cached MinecraftServers.
     */
    default @NotNull Set<MinecraftServer> getMinecraftServers() {
        return this.getTurtles(MinecraftServer.class);
    }

    /**
     * Returns a single {@link MinecraftServer} specified by its id, or {@code null} if no such object is stored in the
     * underlying cache.
     * @param id The unique id of the MinecraftServer.
     * @return The requested MinecraftServer (may be {@code null}).
     * @see MinecraftServer#getId()
     */
    default @Nullable MinecraftServer getMinecraftServerId(long id) {
        return this.getTurtleById(id, MinecraftServer.class);
    }

    /* - - - */

    /**
     * A TurtleClient may not be re-used after shutting it down. Doing so would result in unpredictable behaviour.
     * Resource caches are not emptied on shutdown, and while this is not recommended, they can technically still be
     * accessed afterwards.
     * <p> This method will block the calling thread until the TurtleClient is shut down or the timeout is exceeded.
     * @throws IOException if such an exception is thrown while shutting down an underlying service.
     */
    void shutdown() throws IOException;

    /**
     * Forces this TurtleClient to shut down. A safe disconnect cannot be guaranteed.
     * @throws IOException if such an exception is thrown while shutting down an underlying service.
     */
    void shutdownNow() throws IOException;
}
