package it.unibo.basics;

import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import it.unibo.utils.Tensor;

import java.util.List;
import java.util.stream.Stream;

public class EmbeddingBaseExample {
    public static void main(String[] args) {
        OllamaEmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("mxbai-embed-large")
            .logRequests(true)
            .logResponses(true)
            .build();

        List<Tensor> result = Stream.of("Hello", "how", "are", "you")
            .map(embeddingModel::embed)
            .map(response -> response.content().vector())
            .map(Tensor::fromFloatArray)
            .toList();

        Tensor anotherSentence = Tensor.fromFloatArray(
            embeddingModel.embed("Ciao").content().vector()
        );

        List<Double> distances = result.stream()
            .map(vector -> vector.distance(anotherSentence))
            .toList();
        

        List<Double> similarity = result.stream()
            .map(vector -> vector.cosineSimilarity(anotherSentence))
            .toList();

        System.out.println("Distances: " + distances);
        System.out.println("Similarity: " + similarity);
    }
}
