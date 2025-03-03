package it.unibo.prompt;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class ZeroShotAgent extends BasePromptBasedAgent {
    public ZeroShotAgent(ChatLanguageModel model, String promptBase) {
        super(model, promptBase);
    }

    @Override
    public String ask(String userMessage) {
        return getModel().chat(UserMessage.from(getPromptBase() + userMessage)).aiMessage().text();
    }
}
