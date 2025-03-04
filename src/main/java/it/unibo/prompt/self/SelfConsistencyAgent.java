package it.unibo.prompt.self;

import it.unibo.prompt.PromptBasedAgent;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * An agent that tries to be self-consistent by asking the model multiple times
 * and returning the most common reply.
 * This agent is useful for generating more consistent replies.
 */
public class SelfConsistencyAgent implements PromptBasedAgent {
    private final int consistencyLevel;
    private final PromptBasedAgent agent;
    public SelfConsistencyAgent(PromptBasedAgent wrapped, int consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
        this.agent = wrapped;
    }

    @Override
    public String getPromptBase() {
        return agent.getPromptBase();
    }

    @Override
    public String ask(String userMessage) {
        var replies = IntStream.range(0, consistencyLevel)
            .mapToObj(i -> agent.ask(userMessage))
            .toList();
        var groupedReplies = replies.stream()
            .collect(Collectors.groupingBy(data -> data));
        System.out.println("replies: " + replies.stream().map(data -> data).toList());
        // Find the most common reply
        return groupedReplies.entrySet().stream().max(Comparator.comparingInt(entry -> entry.getValue().size())).get().getKey();
    }

    @Override
    public String toString() {
        return "SelfConsistencyAgent of " + agent.toString();
    }
}
