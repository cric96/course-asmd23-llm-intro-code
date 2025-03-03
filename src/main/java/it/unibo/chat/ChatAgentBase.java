package it.unibo.chat;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.util.ArrayList;
import java.util.List;

class ChatAgentBase implements ChatAgent {
    private final ChatLanguageModel model;
    private final List<ChatMessage> messageFlow;

    public ChatAgentBase(ChatLanguageModel model, List<ChatMessage> initialMessage) {
        this.model = model;
        this.messageFlow = new ArrayList<>();
        this.messageFlow.addAll(initialMessage);
    }

    public String interact(String userMessage) {
        messageFlow.add(UserMessage.from(userMessage));
        return this.interact();
    }

    public String interact() {
        System.out.println("Message flow: " + messageFlow);
        var chatResponse = model.chat(messageFlow);
        String responseText = chatResponse.aiMessage().text();
        messageFlow.add(chatResponse.aiMessage());
        return responseText;
    }

    public void recordMessage(ChatMessage message) {
        messageFlow.add(message);
    }
}
