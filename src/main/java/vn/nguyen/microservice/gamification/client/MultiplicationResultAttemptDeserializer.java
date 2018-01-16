package vn.nguyen.microservice.gamification.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import vn.nguyen.microservice.gamification.client.dto.MultiplicationResultAttempt;

import java.io.IOException;

/**
 * Created by nals on 1/15/18.
 */

public class MultiplicationResultAttemptDeserializer extends JsonDeserializer<MultiplicationResultAttempt>{
    @Override
    public MultiplicationResultAttempt deserialize(JsonParser jsonParser,
                                                   DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);

        return new MultiplicationResultAttempt(jsonNode.get("user").get("alias").asText(),
                jsonNode.get("multiplication").get("factorNumberA").asInt(),
                jsonNode.get("multiplication").get("factorNumberB").asInt(),
                jsonNode.get("resultAttempt").asInt(),
                jsonNode.get("correct").asBoolean());
    }
}
