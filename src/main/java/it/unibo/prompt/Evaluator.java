package it.unibo.prompt;

public interface Evaluator {
    Double compare(String reference, String hypothesis);
}
