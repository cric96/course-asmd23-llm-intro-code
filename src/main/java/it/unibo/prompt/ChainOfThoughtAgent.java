package it.unibo.prompt;

import dev.langchain4j.model.chat.ChatLanguageModel;

import java.util.List;

public class ChainOfThoughtAgent extends BasePromptBasedAgent {

    public ChainOfThoughtAgent(ChatLanguageModel model) {
        super(model, "Let's think step by step");
    }

    @Override
    protected String prepareMessage(String userMessage) {
        return userMessage + getPromptBase();
    }

}
