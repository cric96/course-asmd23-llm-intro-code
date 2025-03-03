package it.unibo.basics

import dev.langchain4j.data.message.{ChatMessage, SystemMessage, UserMessage}
import dev.langchain4j.model.ollama.OllamaChatModel

@main def helloWorldAi(): Unit =
  val model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    //.temperature(0.0)
    .logRequests(true)
    .logResponses(true)
    .modelName("smollm:360m")
    .numPredict(128)
    .build()

  val message = UserMessage.userMessage("Say Hello!")
  val response = model.chat(message)

  println("Token used: " + response.tokenUsage().inputTokenCount())
  println("Toked in output: " + response.tokenUsage().outputTokenCount())

  println("Response: " + response.aiMessage().text())

@main
def helloWorldAiWithTemperature(): Unit =
  val model = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .logRequests(true)
    .logResponses(true)
    .temperature(1.0)
    .modelName("smollm:360m")
    .numPredict(128)
    .build()

  var leftFlow: List[ChatMessage] = List(UserMessage.from("Hey! What is your name?"))
  var rightFlow: List[ChatMessage] = List(SystemMessage.from("Replies shortly, be rude!"))

  for _ <- 1 to 5 do
    val leftChat = model.chat(leftFlow:_*)
    println("Left: " + leftChat.aiMessage().text())
    rightFlow = rightFlow :+ UserMessage.from(leftChat.aiMessage().text())
    val rightChat = model.chat(rightFlow:_*)
    println("Right: " + rightChat.aiMessage().text())
    leftFlow = leftFlow :+ leftChat.aiMessage() :+ UserMessage.from(rightChat.aiMessage().text())
    rightFlow = rightFlow :+ rightChat.aiMessage()



