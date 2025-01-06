package raf.draft.dsw.model.repository.jacksonmodules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.awt.geom.AffineTransform;
import java.io.IOException;

public class AffineTransformModule extends SimpleModule {
    public AffineTransformModule(){
        addSerializer(AffineTransform.class, new AffineTransformSerializer());
        addDeserializer(AffineTransform.class, new AffineTransformDeserializer());
    }

    public static class AffineTransformSerializer extends JsonSerializer<AffineTransform> {
        @Override
        public void serialize(AffineTransform transform, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            double[] flatMatrix = new double[6];
            transform.getMatrix(flatMatrix);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("m00", flatMatrix[0]);
            jsonGenerator.writeNumberField("m10", flatMatrix[1]);
            jsonGenerator.writeNumberField("m01", flatMatrix[2]);
            jsonGenerator.writeNumberField("m11", flatMatrix[3]);
            jsonGenerator.writeNumberField("m02", flatMatrix[4]);
            jsonGenerator.writeNumberField("m12", flatMatrix[5]);
            jsonGenerator.writeEndObject();
        }
    }

    public static class AffineTransformDeserializer extends JsonDeserializer<AffineTransform> {
        @Override
        public AffineTransform deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            TreeNode node = jsonParser.getCodec().readTree(jsonParser);
            double m00 = Double.parseDouble(node.get("m00").toString());
            double m10 = Double.parseDouble(node.get("m10").toString());
            double m01 = Double.parseDouble(node.get("m01").toString());
            double m11 = Double.parseDouble(node.get("m11").toString());
            double m02 = Double.parseDouble(node.get("m02").toString());
            double m12 = Double.parseDouble(node.get("m12").toString());
            return new AffineTransform(m00, m10, m01, m11, m02, m12);
        }
    }
}
