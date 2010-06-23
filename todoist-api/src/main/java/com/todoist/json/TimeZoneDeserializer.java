package com.todoist.json;

import java.io.IOException;
import java.util.TimeZone;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.StdScalarDeserializer;

public class TimeZoneDeserializer extends StdScalarDeserializer<TimeZone> {

    public TimeZoneDeserializer() {
        super(TimeZone.class);
    }

    @Override
    public TimeZone deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_STRING) {
            String value = jp.getText();

            return TimeZone.getTimeZone(value);
        }
        throw ctxt.mappingException(_valueClass);
    }

}
