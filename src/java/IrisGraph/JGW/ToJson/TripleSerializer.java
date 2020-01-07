package IrisGraph.JGW.ToJson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TripleSerializer extends JsonSerializer<TripleDto> {
    public TripleSerializer() {
        super();
    }

    @Override
    public void serialize(TripleDto value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeStringField("name", value.getName());
        jgen.writeObjectField("value", value.getValue());
        jgen.writeEndObject();
    }
}