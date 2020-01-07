package IrisGraph.JGW.ToJson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ProjectionSerializer extends JsonSerializer<ProjectionDto> {
    public ProjectionSerializer() {
        super();
    }

    @Override
    public void serialize(ProjectionDto value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("_class", value.get_class());
        jgen.writeArrayFieldStart("projectionElemList");
        for (String x: value.getProjectionElemList())
            jgen.writeString(x);
        jgen.writeEndArray();
        jgen.writeObjectField(value.getTupleExprDto().get_class(), value.getTupleExprDto());
        jgen.writeEndObject();
    }
}