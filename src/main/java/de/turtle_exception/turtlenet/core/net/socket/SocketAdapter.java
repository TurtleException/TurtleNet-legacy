package de.turtle_exception.turtlenet.core.net.socket;

import com.google.common.collect.Sets;
import de.turtle_exception.turtlenet.core.TurtleClientImpl;
import de.turtle_exception.turtlenet.core.util.logging.NestedLogger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.logging.Level;

public class SocketAdapter {
    private final TurtleClientImpl client;
    private final NestedLogger logger;

    private final ServerSocket server;
    private final Thread listener;

    private final ThreadGroup connectionThreads;

    final Set<P2PConnection> connections = Sets.newConcurrentHashSet();
    final Set<Handshake>     handshakes  = Sets.newConcurrentHashSet();


    public SocketAdapter(TurtleClientImpl client, int port, @NotNull String pass) throws IOException {
        this.client = client;
        this.logger = new NestedLogger("SocketAdapter", client.getLogger());

        this.server = new ServerSocket(port);

        this.connectionThreads = new ThreadGroup("SocketAdapter-clients");

        this.listener = new Thread(() -> {
            logger.log(Level.FINE, "Listening for new connection...");
            Socket socket;
            try {
                socket = server.accept();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Could not establish requested connection.", e);
                return;
            }

            logger.log(Level.FINE, "New socket connection: " + socket.getInetAddress().toString() + ":" + socket.getPort());

            try {
                handshakes.add(new Handshake.Server(this, socket, pass));
            } catch (IOException e) {
                logger.log(Level.WARNING, "Handshake failed.", e);
            }
        }, "SocketAdapter-server");
        this.listener.start();
    }

    @NotNull ThreadGroup getConnectionThreadGroup() {
        return connectionThreads;
    }

    public @NotNull NestedLogger getLogger() {
        return logger;
    }
}
