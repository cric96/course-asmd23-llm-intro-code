package it.unibo.prompt;

/**
 * Represents an agent that uses a base prompt to provide context
 * for conversations.
 */
public interface PromptBasedAgent {
    /**
     * Get the base prompt for the agent.
     * @return The base prompt.
     */
    String getPromptBase();

    /**
     * Ask the agent a question to get a response.
     * @param userMessage The user's message.
     * @return The agent's response.
     */
    String ask(String userMessage);
}
