package de.turtle_exception.turtlenet.core.net.socket;

import de.turtle_exception.turtlenet.core.net.Packet;
import de.turtle_exception.turtlenet.core.util.logging.NestedLogger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

class P2PConnection extends Thread implements Closeable {
    private final NestedLogger logger;
    private final SocketAdapter adapter;
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    /** Encryption key */
    private final String key;

    P2PConnection(@NotNull Handshake handshake, @NotNull String key) throws IOException {
        super(handshake.adapter.getConnectionThreadGroup(), handshake.getName().replace("Handshake", "Connection"));
        this.logger  = handshake.logger;
        this.adapter = handshake.adapter;
        this.socket  = handshake.socket;

        this.out = new DataOutputStream(socket.getOutputStream());
        this.in  = new DataInputStream(socket.getInputStream());

        this.key = key;

        this.start();
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            if (socket.isClosed()) {
                this.close();
                return;
            }

            try {
                int length = in.readInt();
                // TODO: check if length is smaller than minimum length (META_BYTES)

                // TODO: read metadata

                // TODO: read content
                final byte[] content = new byte[length /* - META_BYTES*/];
                in.readFully(content);

                // TODO: handle received packet
            } catch (EOFException e) {
                logger.log(Level.WARNING, "Unexpected EOFException. This usually happens when a connection is abruptly closed.", e);
                this.close();
            } catch (SocketException e) {
                logger.log(Level.WARNING, "Unexpected SocketException", e);
                this.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Could not read input", e);
            }
        }
    }

    @Override
    public void close() {
        this.interrupt();

        try {
            if (!socket.isClosed()) {
                out.writeBytes("END");
                socket.close();
            }
            out.close();
            in.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not close socket (while closing handshake)", e);
        }

        adapter.connections.remove(this);
    }

    /* - - - */

    public synchronized void send(@NotNull Packet packet, boolean encrypt) {

    }
}
