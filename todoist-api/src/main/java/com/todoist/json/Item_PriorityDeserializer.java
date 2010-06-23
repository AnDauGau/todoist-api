package com.todoist.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.StdScalarDeserializer;

import com.todoist.Item.Priority;

public class Item_PriorityDeserializer extends StdScalarDeserializer<Priority> {

    public Item_PriorityDeserializer() {
        super(Priority.class);
    }

    @Override
    public Priority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_NUMBER_INT) {
            int priority = jp.getIntValue();

            return Priority.forValue(priority);
        }
        throw ctxt.mappingException(_valueClass);
    }

}
