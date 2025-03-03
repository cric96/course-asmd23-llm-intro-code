package it.unibo.prompt;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * An agent that tries to be self-consistent by asking the model multiple times
 * and returning the most common reply.
 * This agent is useful for generating more consistent replies.
 */
public class SelfConsistencyAgent extends BasePromptBasedAgent {
    private final int consistencyLevel;

    public SelfConsistencyAgent(ChatLanguageModel model, String promptBase, int consistencyLevel) {
        super(model, promptBase);
        this.consistencyLevel = consistencyLevel;
    }

    @Override
    public String ask(String userMessage) {
        var replies = IntStream.range(0, consistencyLevel)
            .mapToObj(i -> getModel().chat(UserMessage.from(this.prepareMessage(userMessage))))
            .toList();
        var groupedReplies = replies.stream()
            .collect(Collectors.groupingBy(data -> data.aiMessage().text()));
        System.out.println("replies: " + replies.stream().map(data -> data.aiMessage().text()).toList());
        // Find the most common reply
        return groupedReplies.entrySet().stream().max(Comparator.comparingInt(entry -> entry.getValue().size())).get().getKey();
    }
}
