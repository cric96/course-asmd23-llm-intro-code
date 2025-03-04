package it.unibo.prompt.few;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.util.List;

public class FewShotAgentExample {
    public static void main(String[] args) {
        final ChatLanguageModel model = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .logRequests(true)
            .logResponses(true)
            .modelName("smollm")
            .numPredict(128)
            .build();
        final var examples = List.of(
                FewShotAgent.QuestionAnswer.from("Hi there!", "OK"),
                FewShotAgent.QuestionAnswer.from("You won a free vacation!", "SPAM"),
                FewShotAgent.QuestionAnswer.from("Urgent: Claim your prize now!", "SPAM")
        );
        final var empty = List.of();
        final var agent = new FewShotAgent(model, examples);
        final var query = """
            Classify the following email:
            Q: Hey, how’s it going?
            A: ?
        """;
        final var response = agent.ask(query);
        System.out.println("Response: " + response);
    }
}
