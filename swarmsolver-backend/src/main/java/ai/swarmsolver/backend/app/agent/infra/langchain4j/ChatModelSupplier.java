package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;

@FunctionalInterface
public interface ChatModelSupplier {
    ChatLanguageModel create();
}
