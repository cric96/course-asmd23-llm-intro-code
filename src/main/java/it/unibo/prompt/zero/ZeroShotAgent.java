package it.unibo.prompt.zero;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import it.unibo.prompt.BasePromptBasedAgent;

/**
 * A prompt-based agent that can chat with the user with a simple base prompt
 * which gives the agent some context about the conversation.
 */
public class ZeroShotAgent extends BasePromptBasedAgent {
    public ZeroShotAgent(ChatLanguageModel model, String promptBase) {
        super(model, promptBase);
    }

    @Override
    public String ask(String userMessage) {
        return getModel().chat(
            UserMessage.from(this.prepareMessage(userMessage))
        ).aiMessage().text();
    }
    
    @Override
    public String toString() {
        return "ZeroShotAgent";
    }
}
