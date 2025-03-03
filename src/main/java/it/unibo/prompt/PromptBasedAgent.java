package it.unibo.prompt;

public interface PromptBasedAgent {
    String getPromptBase();
    String ask(String userMessage);
}
