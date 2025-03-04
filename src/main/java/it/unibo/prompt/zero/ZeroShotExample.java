package it.unibo.prompt.zero;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ZeroShotExample {
    public static void main(String[] args) {
        final ChatLanguageModel model = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .logRequests(true)
            .logResponses(true)
            .modelName("qwen2.5:3b")
            .numPredict(128)
            .build();
        final var zeroShot = new ZeroShotAgent(model, "Just reply with the RIGHT number.");
        final var query = """
            Q: Today I have 6 apples. Tomorrow I buy 3 more. Yesterday I ate 6
            apples, How many apples do I have (today)?
        """;
        for (int i = 0; i < 20; i++) {
            var response = zeroShot.ask(query);
            System.out.println("Response: " + response);
        }
    }
}
