package it.unibo.prompt;

import dev.langchain4j.model.embedding.DimensionAwareEmbeddingModel;
import it.unibo.utils.Vector;

public class CosineEmbeddingEvaluator implements Evaluator {
    private DimensionAwareEmbeddingModel model;

    public CosineEmbeddingEvaluator(DimensionAwareEmbeddingModel model) {
        this.model = model;
    }
    
    public Double compare(String reference, String hypothesis) {
        var refEmbedding = Vector.fromFloatArray(
            model.embed(reference).content().vector()
        );
        var hypEmbedding = Vector.fromFloatArray(
            model.embed(hypothesis).content().vector()
        );
        return refEmbedding.cosineSimilarity(hypEmbedding);
    }
}
