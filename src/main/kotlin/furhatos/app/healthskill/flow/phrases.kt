package furhatos.app.healthskill.flow

import furhatos.app.healthskill.getRandomString
import furhatos.util.joinToEnum

var phrases : Phrases = DefaultPhrases()

abstract class Phrases {
    abstract val howCanIhelp : String
    abstract fun canIhelpWithAnythingElse() : String
    abstract val okay : String
    abstract val yes : String
    abstract val no : String
    abstract val greeting : String
    abstract val reGreeting : String
    abstract val goodbye : String
    abstract fun presentOptions(options : List<String>) : String
    abstract fun presentAdditionalOptions(options : List<String>) : String
    abstract val canDoThat : String
    abstract val canNotDoThat : String
    abstract val askForSymptoms : String
    abstract val generalEstimate : String
    abstract val refuseAnswer : String
}

open class DefaultPhrases : Phrases() {
    override val okay: String = "okay"
    override val yes: String = getRandomString(listOf(
            "yes",
            "yeah"))
    override val no: String = "no"
    override val howCanIhelp = getRandomString(listOf(
            "how can I help?",
            "what can I do for you?",
            "What can I do to help?",
            "How can I help you?"))
    override fun presentOptions(options: List<String>) = getRandomString(listOf(
            "you can for example ask me ",
            "you can ask me "
    )) + options.joinToEnum("or")
    override fun presentAdditionalOptions(options: List<String>) = "you can also ask me " + options.joinToEnum("or")
    override fun canIhelpWithAnythingElse() = getRandomString(listOf(
            "Can I do anything else for you?",
            "What else can I do for you?",
            "Is there anything else I can do for you?"))
    override val greeting = getRandomString(listOf(
            "Hello there!",
            "Hi there!",
            "Hello!"))
    override val reGreeting = getRandomString(listOf(
            "Hey! Welcome back."))
    override val goodbye = getRandomString(listOf(
            "Bye!",
            "Goodbye!",
            "Bye bye,"))
    override val canDoThat = getRandomString(listOf(
            "I can",
            "that, I can do",
            "absolutely"
    ))
    override val canNotDoThat = "sorry, I can't do that"
    override val askForSymptoms = getRandomString(listOf(
            "Do you feel ill?",
            "Are you feeling sick?"))
    override val generalEstimate = "Just give me your best estimate."
    override val refuseAnswer = "Ok but please keep in mind that missing this information may affect the quality of future recommendations."
}
