package raf.draft.dsw.model.repository.jacksonmodules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.awt.geom.Point2D;
import java.io.IOException;

public class Point2DModule extends SimpleModule {
    public Point2DModule() {
        addSerializer(Point2D.class, new Point2DSerializer());
        addDeserializer(Point2D.class, new Point2DDeserializer());
    }

    public static class Point2DSerializer extends JsonSerializer<Point2D> {
        @Override
        public void serialize(Point2D point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("x", point.getX());
            jsonGenerator.writeNumberField("y", point.getY());
            jsonGenerator.writeEndObject();
        }
    }

    public static class Point2DDeserializer extends JsonDeserializer<Point2D> {
        @Override
        public Point2D deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            TreeNode node = jsonParser.getCodec().readTree(jsonParser);
            double x = Double.parseDouble(node.get("x").toString());
            double y = Double.parseDouble(node.get("y").toString());
            return new Point2D.Double(x, y);
        }
    }
}
