import ai.swarmsolver.backend.app.agent.infra.langchain4j.*;
import dev.langchain4j.model.openai.OpenAiChatModel;
import java.time.Duration;

def chatModelSupplier = {
    Duration timeoutDuration = Duration.ofSeconds(60);
    OpenAiChatModel chatModel = OpenAiChatModel.builder()
            .apiKey(keys.getKey('OPENAI_API_KEY'))
            .timeout(timeoutDuration)
            .build();
} as ChatModelSupplier

def agentSpecification = new LangChain4jAgentSpecification(chatModelSupplier, "java developer")

agentSpecification