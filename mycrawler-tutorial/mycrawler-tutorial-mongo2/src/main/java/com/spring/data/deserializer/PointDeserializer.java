package com.spring.data.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.geo.Point;

import java.io.IOException;

/**
 * Created by Ghazi Naceur on 28/06/2017.
 * <p>
 * A custom deserializer for Jackson used to deserialize Point type, which is not handled by Jackson by
 * default like the simple types.
 * </p>
 */
public class PointDeserializer extends JsonDeserializer<Point> {

    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        return new Point(node.get(0).asDouble(),node.get(1).asDouble());
    }
}
