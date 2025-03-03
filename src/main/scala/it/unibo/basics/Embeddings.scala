package it.unibo.basics

import dev.langchain4j.model.ollama.OllamaEmbeddingModel
import it.unibo.utils.Tensor.*

import scala.io.Source

val embeddingModel = OllamaEmbeddingModel.builder()
  .baseUrl("http://localhost:11434")
  .modelName("mxbai-embed-large")
  .logRequests(true)
  .logResponses(true)
  .build()

@main def baseEmbeddings(): Unit =

  val result = ("Hello" :: "how" :: "are" :: "you" :: Nil)
    .map { embeddingModel.embed }
    .map { response => response.content().vector() }
    .map { vector => vector.toList }

  val anotherSentence = embeddingModel.embed("Ciao").content().vector()

  val distances = result.map { vector => vector.distance(anotherSentence) }
  val similarity = result.map { vector => vector.cosineSimilarity(anotherSentence) }

  println(s"Distances: $distances")
  println(s"Similarity: $similarity")

@main def visualizeEmbeddings(): Unit =
  val datasetFromResource = Source.fromResource("dataset.txt").getLines.toList

  val embeddedAll = datasetFromResource
    .map { sentence => embeddingModel.embed(sentence) }
    .map { response => response.content().vector().map(_.toDouble) }
    .toArray[Array[Double]]
  val questionOnSpace = embeddingModel.embed("Dov'è Giove?").content().vector().map(_.toDouble)
  val questionOnAnime = embeddingModel.embed("Voglio sapere di più su Naruto!").content().vector().map(_.toDouble)
  // pca
  val combineAll = embeddedAll :+ questionOnSpace :+ questionOnAnime
  val tsne = smile.manifold.TSNE(combineAll, 2)
  val colors = Array.fill(embeddedAll.length)(0) ++ Array(1, 2)
  smile.plot.show(
    smile.plot.swing.ScatterPlot.of(tsne.coordinates(), colors, 'x').canvas()
  )(canvas => canvas.window().setVisible(true))

  // search
  val nearest = embeddedAll
    .map { vector => vector.distance(questionOnAnime) }
    .zipWithIndex
    .sortBy { case (distance, _) => distance }
    .take(5)
    .map { case (_, index) => datasetFromResource(index) }
    .toList

  println(s"Nearest to Naruto: $nearest")
