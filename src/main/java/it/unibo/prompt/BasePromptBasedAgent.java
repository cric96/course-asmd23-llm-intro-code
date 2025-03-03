package it.unibo.prompt;

import dev.langchain4j.model.chat.ChatLanguageModel;

public abstract class BasePromptBasedAgent implements PromptBasedAgent {
    private final String promptBase;
    private final ChatLanguageModel model;
    public BasePromptBasedAgent(ChatLanguageModel model, String promptBase) {
        this.promptBase = promptBase;
        this.model = model;
    }
    
    protected ChatLanguageModel getModel() {
        return model;
    }

    public String getPromptBase() {
        return promptBase;
    }
}
