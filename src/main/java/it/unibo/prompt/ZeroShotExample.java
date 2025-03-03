package it.unibo.prompt;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ZeroShotExample {
    public static void main(String[] args) {
        ChatLanguageModel model = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            //.temperature(0.0)
            .logRequests(true)
            .logResponses(true)
            .modelName("qwen2.5:3b")
            .numPredict(128)
            .build();

        var selfConsistency = new ZeroShotAgent(model, "Just reply with the RIGHT number.");

        var query = """
            Q: Today I have 6 apples. Tomorrow I buy 3 more. Yesterday I ate 6
            apples, How many apples do I have (today)?
        """;
        for (int i = 0; i < 20; i++) {
            var response = selfConsistency.ask(query);
            System.out.println("Response: " + response);
        }

        // code generation example
        
    }
}
