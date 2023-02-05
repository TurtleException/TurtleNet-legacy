package de.turtle_exception.turtlenet.core.util.crypto;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/** Simple utility for a challenge-response protocol. */
public class ChallengeResponse {
    private final SecureRandom random = new SecureRandom();

    private final MessageDigest hashFunc;
    private final byte[] secret;
    private final int length;

    public ChallengeResponse(byte[] secret, @NotNull String algorithm, int length) throws NoSuchAlgorithmException {
        this.hashFunc = MessageDigest.getInstance(algorithm);
        this.secret = secret;
        this.length = length;
    }

    public byte[] generateChallenge() {
        byte[] challenge = new byte[length];
        random.nextBytes(challenge);
        return challenge;
    }

    public byte[] calculateResponse(byte[] challenge) {
        hashFunc.update(secret);
        hashFunc.update(challenge);
        return hashFunc.digest();
    }

    public boolean verifyResponse(byte[] challenge, byte[] response) {
        byte[] expected = calculateResponse(challenge);
        return Arrays.equals(expected, response);
    }
}
