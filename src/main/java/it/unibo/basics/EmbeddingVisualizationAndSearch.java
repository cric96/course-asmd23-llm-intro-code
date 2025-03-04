package it.unibo.basics;

import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import it.unibo.utils.Vector;
import smile.plot.swing.ScatterPlot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class EmbeddingVisualizationAndSearch {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        List<String> datasetFromResource;
        try {
            datasetFromResource = Files.readAllLines(Path.of(
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("dataset.txt")).toURI()
            ));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to read dataset", e);
        }

        OllamaEmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("mxbai-embed-large")
            .logRequests(true)
            .logResponses(true)
            .build(); // Assume this is implemented

        List<Vector> datasetEmbeddings = datasetFromResource.stream()
            .map(embeddingModel::embed).map(response -> response.content().vector())
            .map(Vector::fromFloatArray)
            .toList();


        Vector questionOnSpace = Vector.fromFloatArray(
            embeddingModel.embed("Where is Jupyter?").content().vector()
        );

        Vector questionOnAnime = Vector.fromFloatArray(
            embeddingModel.embed("Give more info about Naruto!").content().vector()
        );

        double[][] allEmbeddings = Stream.concat(Stream.of(questionOnSpace, questionOnAnime), datasetEmbeddings.stream())
            .map(Vector::getData).toArray(double[][]::new);

        var tsneFlatten = smile.manifold.TSNE.fit(allEmbeddings);

        int[] labels = Stream.concat(Stream.of(1, 2), Stream.generate(() -> 0).limit(datasetEmbeddings.size()))
            .mapToInt(Integer::intValue).toArray();

        var plot = ScatterPlot.of(tsneFlatten.coordinates(), labels, 'x');

        var canvas = plot.canvas();
        canvas.window().setVisible(true);

        var finClosestToNaruto = findNClosest(questionOnAnime, datasetEmbeddings, 5);
        var finClosestToJupyter = findNClosest(questionOnSpace, datasetEmbeddings, 5);

        System.out.println("Closest to Naruto: " + finClosestToNaruto.stream().map(datasetFromResource::get).toList());
        System.out.println("Closest to Jupyter: " + finClosestToJupyter.stream().map(datasetFromResource::get).toList());
    }

    private static List<Integer> findNClosest(Vector question, List<Vector> dataset, int howMuch) {
        var indexes = Stream.iterate(0, i -> i + 1).limit(dataset.size()).toList();
        return indexes.stream()
            .sorted(Comparator.comparingDouble(a -> dataset.get(a).cosineSimilarity(question)))
            .limit(howMuch)
            .toList();
    }
}
