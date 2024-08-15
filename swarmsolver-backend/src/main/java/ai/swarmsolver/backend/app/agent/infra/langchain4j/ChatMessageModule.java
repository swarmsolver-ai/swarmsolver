package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ChatMessageModule extends SimpleModule {
    public ChatMessageModule() {
        addSerializer(ChatMessage.class, new MyChatMessageSerializer());
        addDeserializer(ChatMessage.class, new MyChatMessageDeserializer());
    }

    private static class MyChatMessageSerializer extends JsonSerializer<ChatMessage> {
        @Override
        public void serialize(ChatMessage value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            String json = ChatMessageSerializer.messageToJson(value);
            gen.writeRawValue(json);
        }
    }

    private static class MyChatMessageDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<ChatMessage> {
        @Override
        public ChatMessage deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            String json = p.readValueAsTree().toString();
            return ChatMessageDeserializer.messageFromJson(json);
        }
    }
}
