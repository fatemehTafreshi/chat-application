package com.spring.chatApp.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JacksonLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    private static final long serialVersionUID = 9152770723354619045L;

    public JacksonLocalDateTimeDeserializer() {
        this(null);
    }

    protected JacksonLocalDateTimeDeserializer(Class<LocalDateTime> type) {
        super(type);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        if (parser.getValueAsString().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(parser.getValueAsString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
    }
}