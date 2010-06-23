package com.todoist.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.StdScalarDeserializer;

import com.todoist.Color;

public class ColorDeserializer extends StdScalarDeserializer<Color> {

    public ColorDeserializer() {
        super(Color.class);
    }

    @Override
    public Color deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_STRING) {
            String value = jp.getText();

            for (Color color : Color.values()) {
                if (value.equals(color.getValue())) {
                    return color;
                }
            }
        } else if (curr == JsonToken.VALUE_NUMBER_INT) {
            int index = jp.getIntValue();
            for (Color color : Color.values()) {
                if (index == color.getIndex()) {
                    return color;
                }
            }
        }
        throw ctxt.mappingException(_valueClass);
    }

}
