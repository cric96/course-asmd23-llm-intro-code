package it.unibo.prompt;

import dev.langchain4j.model.chat.ChatLanguageModel;

import java.util.List;

public class FewShotAgent extends BasePromptBasedAgent {

    public FewShotAgent(
        ChatLanguageModel model,
        List<QuestionAnswer> questionAnswers
    ) {
        super(model, promptFromKnowledge(questionAnswers));
    }


    public static class QuestionAnswer {
        private String question;
        private String answer;
        public QuestionAnswer(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public static QuestionAnswer from(String question, String answer) {
            return new QuestionAnswer(question, answer);
        }
    }

    private static String promptFromKnowledge(List<QuestionAnswer> questions) {
        return "Giving this knowledge:" + questions.stream()
            .map(qa -> "Q:" + qa.question + "A:" + qa.answer)
            .reduce("", (acc, qa) -> acc + " \n " + qa) + "\n reply to the following question:";
    }
}
