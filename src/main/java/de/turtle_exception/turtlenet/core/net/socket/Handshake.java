package de.turtle_exception.turtlenet.core.net.socket;

import de.turtle_exception.turtlenet.core.net.NetworkAdapter;
import de.turtle_exception.turtlenet.core.util.crypto.ChallengeResponse;
import de.turtle_exception.turtlenet.core.util.crypto.EncryptionV1;
import de.turtle_exception.turtlenet.core.util.crypto.PassGen;
import de.turtle_exception.turtlenet.core.util.logging.NestedLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;

// TODO: implement timeout
abstract class Handshake extends Thread implements Closeable {
    /** This logger will be used by the {@link P2PConnection} once the handshake is done. */
    final NestedLogger logger;
    final SocketAdapter adapter;
    final Socket socket;

    protected final String pass;

    protected final PrintStream out;
    protected final BufferedReader in;
    protected String buffer = null;

    protected final ChallengeResponse cr;

    private Handshake(@NotNull SocketAdapter adapter, @NotNull Socket socket, @NotNull String pass) throws IOException {
        super(adapter.getConnectionThreadGroup(), "Handshake/" + socket.getInetAddress() + ":" + socket.getPort());

        this.logger = new NestedLogger("Connection/" + socket.getInetAddress() + ":" + socket.getPort(), adapter.getLogger());

        this.pass = pass;

        this.adapter = adapter;
        this.socket = socket;

        this.out = new PrintStream(socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        try {
            this.cr = new ChallengeResponse(pass.getBytes(StandardCharsets.ISO_8859_1), "SHA-256", 256);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }

        this.logger.log(Level.FINE, "Initiating handshake...");
        this.start();
    }

    @Override
    public final void run() {
        try {
            String key = this.handle();
            if (key != null)
                this.complete(key);
            else
                this.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not complete handshake due to an IOException.", e);
            this.close();
        }
    }

    protected abstract @Nullable String handle() throws IOException;

    @Override
    public final void close() {
        try {
            if (!socket.isClosed()) {
                out.println("END");
                socket.close();
            }
            out.close();
            in.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not close socket (while closing handshake)", e);
        }

        adapter.handshakes.remove(this);
    }

    private void complete(@NotNull String key) throws IOException {
        logger.log(Level.FINE, "Handshake completed successfully.");

        adapter.handshakes.remove(this);
        adapter.connections.add(new P2PConnection(this, key));
    }

    static class Client extends Handshake {
        Client(@NotNull SocketAdapter adapter, @NotNull Socket socket, @NotNull String pass) throws IOException {
            super(adapter, socket, pass);
        }

        @Override
        public String handle() throws IOException {
            out.println("protocol " + NetworkAdapter.PROTOCOL_VERSION);

            // expected: challenge
            buffer = in.readLine();
            if (buffer.equals("END"))
                return null;

            byte[] challenge = buffer.getBytes(StandardCharsets.ISO_8859_1);
            byte[] response  = cr.calculateResponse(challenge);

            out.println(new String(response, StandardCharsets.ISO_8859_1));

            // expected: encrypted key
            buffer = in.readLine();
            if (buffer.equals("END"))
                return null;

            try {
                String key = EncryptionV1.decrypt(buffer, pass);

                String encryptedOK = EncryptionV1.encrypt("OK", key);
                out.println(encryptedOK);

                // expected: "OK"
                buffer = in.readLine();
                if (!buffer.equals("OK"))
                    return null;

                return key;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException |
                     NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                logger.log(Level.WARNING, "Encryption error", e);
                return null;
            }
        }
    }

    static class Server extends Handshake {
        public Server(@NotNull SocketAdapter adapter, @NotNull Socket socket, @NotNull String pass) throws IOException {
            super(adapter, socket, pass);
        }

        @Override
        public String handle() throws IOException {
            // TODO: is this blocking?
            // expected: "protocol n" with n being the protocol version
            buffer = in.readLine();

            try {
                int remoteVersion = Integer.parseInt(buffer);
                if (remoteVersion != NetworkAdapter.PROTOCOL_VERSION) {
                    String versions = "(L." + NetworkAdapter.PROTOCOL_VERSION + "/R." + remoteVersion + ")";
                    if (remoteVersion < NetworkAdapter.PROTOCOL_VERSION)
                        logger.log(Level.WARNING, "Protocol conflict: Remote version outdated. " + versions);
                    if (remoteVersion > NetworkAdapter.PROTOCOL_VERSION)
                        logger.log(Level.WARNING, "Protocol conflict: Local version outdated. " + versions);
                    return null;
                }
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Protocol conflict: Could not parse version '" + buffer + "'", e);
                return null;
            }

            byte[] challenge = cr.generateChallenge();
            out.println(new String(challenge, StandardCharsets.ISO_8859_1));

            // expected: challenge response hash
            buffer = in.readLine();

            byte[] response = buffer.getBytes(StandardCharsets.ISO_8859_1);
            if (!cr.verifyResponse(challenge, response))
                return null;

            // VERIFIED - encrypted key transfer may continue

            try {
                String key = PassGen.generate(256);
                String encryptedKey = EncryptionV1.encrypt(key, pass);

                out.println(encryptedKey);

                // expected: encrypted "OK"
                buffer = in.readLine();
                if (buffer.equals("END"))
                    return null;

                String decryptedOK = EncryptionV1.decrypt(buffer, key);
                if (!decryptedOK.equals("OK"))
                    return null;

                out.println("OK");
                return key;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException |
                     NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                logger.log(Level.WARNING, "Encryption error", e);
                return null;
            }
        }
    }
}
