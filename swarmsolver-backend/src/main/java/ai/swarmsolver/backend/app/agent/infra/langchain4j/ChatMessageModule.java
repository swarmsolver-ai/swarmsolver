package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.langchain4j.data.message.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

class ChatMessageDeserializer extends StdDeserializer<ChatMessage> {
    public ChatMessageDeserializer() {
        super(ChatMessage.class);
    }

    @Override
    public ChatMessage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String type = node.get("type").asText();
        String text = node.get("text").asText();

        switch (type) {
            case "SYSTEM":
                return new SystemMessage(text);
            case "USER":
                String name = node.get("name").asText();
                return new UserMessage(name, text);
            case "AI":
                return new AiMessage(text);
            case "TOOL_EXECUTION_RESULT":
                String id = node.get("id").asText();
                String toolName = node.get("toolName").asText();
                return new ToolExecutionResultMessage(id, toolName, text);
            default:
                throw new IllegalArgumentException("Unknown message type: " + type);
        }
    }
}
class SystemMessageSerializer extends StdSerializer<SystemMessage> {
    public SystemMessageSerializer() {
        super(SystemMessage.class);
    }

    @Override
    public void serialize(SystemMessage value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "SYSTEM");
        gen.writeStringField("text", value.text());
        gen.writeEndObject();
    }
}

class UserMessageSerializer extends StdSerializer<UserMessage> {
    public UserMessageSerializer() {
        super(UserMessage.class);
    }

    @Override
    public void serialize(UserMessage value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "USER");
        gen.writeStringField("name", value.name());
        gen.writeStringField("text", value.singleText());
        gen.writeEndObject();
    }
}

class AiMessageSerializer extends StdSerializer<AiMessage> {
    public AiMessageSerializer() {
        super(AiMessage.class);
    }

    @Override
    public void serialize(AiMessage value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "AI");
        gen.writeStringField("text", value.text());
        // Note: We're not serializing toolExecutionRequest here as it's not clear how to handle it
        gen.writeEndObject();
    }
}

class ToolExecutionResultMessageSerializer extends StdSerializer<ToolExecutionResultMessage> {
    public ToolExecutionResultMessageSerializer() {
        super(ToolExecutionResultMessage.class);
    }

    @Override
    public void serialize(ToolExecutionResultMessage value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "TOOL_EXECUTION_RESULT");
        gen.writeStringField("toolName", value.toolName());
        gen.writeStringField("text", value.text());
        gen.writeEndObject();
    }
}

public class ChatMessageModule extends SimpleModule {
    public ChatMessageModule() {
        super("ChatMessageModule");

        // Register serializers
        addSerializer(SystemMessage.class, new SystemMessageSerializer());
        addSerializer(UserMessage.class, new UserMessageSerializer());
        addSerializer(AiMessage.class, new AiMessageSerializer());
        addSerializer(ToolExecutionResultMessage.class, new ToolExecutionResultMessageSerializer());

        // Register deserializers
        addDeserializer(ChatMessage.class, new ChatMessageDeserializer());
    }
}