package it.unibo.utils;

public class Tensor {
    private final double[] data;



    private Tensor(double[] data) {
        this.data = data;
    }

    public static Tensor fromFloatArray(float[] data) {
        double[] doubleData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            doubleData[i] = data[i];
        }
        return new Tensor(doubleData);
    }

    public static Tensor fromDoubleArray(double[] data) {
        return new Tensor(data);
    }

    public double[] getData() {
        return data;
    }

    public double distance(Tensor other) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i] - other.getData()[i], 2);
        }
        return Math.sqrt(sum);
    }

    public double cosineSimilarity(Tensor other) {
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
}
