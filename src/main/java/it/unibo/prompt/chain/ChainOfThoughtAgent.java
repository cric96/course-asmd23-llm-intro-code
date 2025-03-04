package it.unibo.prompt.chain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import it.unibo.prompt.BasePromptBasedAgent;

public class ChainOfThoughtAgent extends BasePromptBasedAgent {

    public ChainOfThoughtAgent(ChatLanguageModel model) {
        super(model, "Let's think step by step");
    }

    @Override
    protected String prepareMessage(String userMessage) {
        return userMessage + getPromptBase();
    }
    
    @Override
    public String toString() {
        return "ChainOfThoughtAgent";
    }
}
