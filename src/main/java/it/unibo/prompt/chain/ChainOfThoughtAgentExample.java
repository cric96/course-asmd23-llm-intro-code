package it.unibo.prompt.chain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ChainOfThoughtAgentExample {
    public static void main(String[] args) {
        final ChatLanguageModel model = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .logRequests(true)
            .logResponses(true)
            .modelName("smollm")
            .numPredict(128)
            .build();
        final var selfConsistency = new ChainOfThoughtAgent(model);
        final var query = """
            Q: Today I have 6 apples. Tomorrow I buy 3 more. Yesterday I ate 6
            apples, How many apples do I have TODAY?
        """;
        final var response = selfConsistency.ask(query);
        System.out.println("Response: " + response);
    }
}
