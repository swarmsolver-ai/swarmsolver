import ai.swarmsolver.backend.app.agent.infra.langchain4j.*;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import java.time.Duration;

// Open AI agent specification

def openAIChatModel = {
    Duration timeoutDuration = Duration.ofSeconds(60);
    OpenAiChatModel chatModel = OpenAiChatModel.builder()
            .apiKey(keys.getKey('OPENAI_API_KEY'))
            .timeout(timeoutDuration)
            .build();
} as ChatModelSupplier

def openAIAgent = new LangChain4jAgentSpecification("OpenAI Agent", openAIChatModel, "java developer", null)

// Anthropic (Claude) agent specification

def anthropicModel = {
    Duration timeoutDuration = Duration.ofSeconds(60);
    AnthropicChatModel chatModel = AnthropicChatModel.builder()
            .apiKey(keys.getKey('ANTHROPIC_API_KEY'))
            .timeout(timeoutDuration)
            .build();
} as ChatModelSupplier

def anthropicAgent = new LangChain4jAgentSpecification("Claude Agent", anthropicModel, "java developer", null)

// return agent specifications
[openAIAgent, anthropicAgent]