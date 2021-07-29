package com.xrp.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

    public LocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(final LocalDateTime value,
                          final JsonGenerator generator,
                          final SerializerProvider provider) throws IOException {
        if (value != null) {
            final long seconds = value.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli() / 1000;
            generator.writeNumber(seconds);
        } else {
            generator.writeNull();
        }
    }
}
