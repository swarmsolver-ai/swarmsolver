package ai.swarmsolver.backend.app.agent.infra.langchain4j;

public enum State {
    CREATED,
    READY_TO_ACCEPT_MESSAGES,
    CALLING_CHAT_MODEL,
    PROCESSING_TOOL_REQUESTS,
    DONE_EXECUTING_TOOL_REQUESTS,
    WAITING_CALLBACK,
    DONE_PROCESSING_CHAT_MODEL;
}
