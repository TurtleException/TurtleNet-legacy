package de.turtle_exception.turtlenet.core.util;

public final class TypeUtil {
    private TypeUtil() { }

    public static long bytesToLon(final byte[] bytes) {
        return bytesToLong(bytes, 0);
    }

    public static long bytesToLong(final byte[] bytes, int index) throws ArrayIndexOutOfBoundsException {
        long l = 0L;
        for (int i = 0; i < Long.BYTES; i++) {
            l <<= Byte.SIZE;
            l |= (bytes[index + i] & 0xFF);
        }
        return l;
    }

    public static byte[] longToBytes(long l) {
        final byte[] arr = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            arr[i] = (byte)(l & 0xFF);
            l >>= Byte.SIZE;
        }
        return arr;
    }
}
