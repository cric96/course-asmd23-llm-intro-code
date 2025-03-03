package it.unibo.utils;

/**
 * A simple class to represent a vector of doubles.
 * It provides methods to calculate the distance and cosine similarity between two vectors.
 */
public class Vector {
    private final double[] data;

    private Vector(double[] data) {
        this.data = data;
    }

    /**
     * Create a Vector from an array of floats.
     * @param data the array of floats
     * @return the Vector created from the data
     */
    public static Vector fromFloatArray(float[] data) {
        double[] doubleData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            doubleData[i] = data[i];
        }
        return new Vector(doubleData);
    }

    /**
     * Create a Vector from an array of doubles.
     * @param data the array of doubles
     * @return the Vector created from the data
     */
    public static Vector fromDoubleArray(double[] data) {
        return new Vector(data);
    }

    /**
     * Get the internal representation of the Vector.
     * @return the array of doubles
     */
    public double[] getData() {
        return data;
    }

    /**
     * Compute the Euclidean distance between this vector and another vector.
     * The two vectors must have the same length.
     * @param other the other vector to compute the distance from
     * @return the Euclidean distance between the two vectors
     */
    public double distance(final Vector other) {
        checkLength(other);
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i] - other.getData()[i], 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Compute the cosine similarity between this vector and another vector.
     * The two vectors must have the same length.
     * Cosine similarity is a measure of similarity between two non-zero vectors of an inner product space.
     * @param other the other vector to compute the cosine similarity from
     * @return the cosine similarity between the two vectors
     */
    public double cosineSimilarity(final Vector other) {
        checkLength(other);
        double dotProduct = 0;
        double normA = 0;
        double normB = 0;
        for (int i = 0; i < data.length; i++) {
            dotProduct += data[i] * other.getData()[i];
            normA += Math.pow(data[i], 2);
            normB += Math.pow(other.getData()[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private void checkLength(final Vector other) {
        if (data.length != other.getData().length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }
    }
}
