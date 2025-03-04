package it.unibo.prompt.self;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import it.unibo.prompt.zero.ZeroShotAgent;

public class SelfConsistencyExample {
    public static void main(String[] args) {
        final ChatLanguageModel model = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .logRequests(true)
            .logResponses(true)
            .modelName("qwen2.5:1.5b")
            .numPredict(128)
            .build();
        final var zeroShot = new ZeroShotAgent(model, "Just reply with the RIGHT number.");
        final var selfConsistency = new SelfConsistencyAgent(zeroShot, 10);
        final var query = """
            Q: Today I have 6 apples. Tomorrow I buy 3 more. Yesterday I ate 6
            apples, How many apples do I have TODAY?
        """;
        final var response = selfConsistency.ask(query);
        System.out.println("Response: " + response);
    }
}
