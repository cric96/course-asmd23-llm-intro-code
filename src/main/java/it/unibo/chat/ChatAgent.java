package it.unibo.chat;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.util.List;

public interface ChatAgent {
    String interact(String userMessage);
    String interact();
    void recordMessage(ChatMessage message);

    static ChatAgent createChatAgent(ChatLanguageModel model, List<ChatMessage> initialMessage) {
        return new ChatAgentBase(model, initialMessage);
    }
}
