package com.spring.chatApp.configuration;

import java.nio.ByteBuffer;
import java.util.UUID;

public class CommonMethods {

    public static UUID uuidFixEndian(byte[] senderIds) {
        return new UUID(
                ByteBuffer.allocate(8).put(0, senderIds, 0, 8).getLong(),
                ByteBuffer.allocate(8).put(0, senderIds, 8, 8).getLong()
        );
    }
}
