package furhatos.app.healthskill.nlu
import furhatos.nlu.EnumEntity
import furhatos.nlu.GenericEnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.TextGenerator
import furhatos.nlu.common.Number
import furhatos.util.Language

class intents {
    class TellSymptoms() : Intent(), TextGenerator{
        var mainSymptoms : MainSymptom? = null
        var minorSymptom : MinorSymptom? = null
        var degree : Number = Number(-1)
        var painLevel : Number = Number(-1)
        override fun getExamples(lang: Language): List<String> {
            return listOf("@mainSymptoms","I feel @mainSymptoms and @minorSymptom","I think I have @mainSymptoms","I believe I have@minorSymptom","I have a @mainSymptoms","I think it is an @mainSymptoms","I have @mainSymptoms on my back"
                    ,"possibly @mainSymptoms","I think I have a fever of @degree degrees", "I have a fever of @degree degrees", "I get a fever and the temperature is @degree degrees","I am @age years old","I think I also have @minorSymptom",
                    "I fell it's level @painLevel","@painLevel","level @painLevel","I think it's @painLevel")
        }
        override fun toText(lang: Language): String {
            return generate(lang, "Symptoms are $mainSymptoms" )
        }
    }
    class forFurtherHelp() : Intent(){
        override fun getExamples(lang: Language): List<String> {
            return listOf(" I feel ill", "I want some more advice", "I feel uncomfortable")
        }
    }
    class TellSex(val sex: Sex ?= null):Intent(){
        override fun getExamples(lang: Language): List<String> {
            return listOf("I am @sex","@sex", "I'm @sex", "I'm a @sex", "I am @sex", "My sex is @sex")
        }
    }
    class Sex: EnumEntity(){
        override fun getEnum(lang:Language): List<String> {
            return listOf("male","female","other")
        }
    }
    class TellAge(val age: Number = Number(-1)) : Intent(){
        override fun getExamples(lang: Language): List<String> {
            return listOf("I am @age years old now","@age","@age years old","I'm @age")
        }
    }

    class MainSymptom: EnumEntity(stemming = true, speechRecPhrases = true){
        override fun getEnum(lang:Language): List<String>{
            return listOf("fever","headache","vomiting","muscle stress", "back pain", "toothache","dry eyes","angina","diarrhea","dry ices")
        }
    }
    class MinorSymptom: EnumEntity(stemming = true, speechRecPhrases = true) {
        override fun getEnum(lang: Language): List<String> {
            return listOf("cough", "sore throat", "stomachache", "cough", "dehydration", "dizzy", "dizziness", "sprained ankle", "stomach ache", "muscle ache")
        }
    }
    class TellMinor(val minor:MinorSymptom? = null) : Intent(){
        override fun getExamples(lang: Language): List<String> {
            return listOf("@minor","I think I also have @minor","I also feel @minor", "@minor is my another symptom"
                    , "It is @minor I believe","I believe it is @minor")
        }
    }
    class TellPainlevel(val painLevel : Number = Number(-1)): Intent(){
        override fun getExamples(lang: Language): List<String> {
            return listOf("@painLevel","I think it is @painLevel","It should be @painLevel","It is @painLevel","I guess it is @painLevel")
        }
    }
    class TellDegree(val degree : Number = Number(-1)) : Intent(){
        var degreeUnit: DegreeUnitEntity? = null
        override fun getExamples(lang: Language): List<String> {
            return listOf("@degree", "@degree @degreeUnit","My body temperature is @degree @degreeUnit", "It is @degree @degreeUnit", "I have a fever of @degree @degreeUnit",
                    "My body temperature is @degree", "It is @degree", "I have a fever of @degree")
        }
    }

    class DegreeUnitEntity : EnumEntity() {
        override fun getEnum(lang: Language): List<String> {
            return listOf("celsius", "degrees", "degree","degree celsius"
            )
        }
    }

    class TellWeight : Intent() {
        var weight: Number = Number(-1)
        var weightUnit: WeightUnitEntity? = null
        override fun getExamples(lang: Language): List<String> {
            return listOf(
                    "@weight",
                    "@weight @weightUnit",
                    "I weigh @weight",
                    "I weigh @weight @weightUnit",
                    "like @weight @weightUnit",
                    "around @weight @weightUnit")
        }
    }

    class WeightUnitEntity : EnumEntity() {
        override fun getEnum(lang: Language): List<String> {
            return listOf(
                    "kilo:kilo, kilos, kgs, kg, kilogramme, kilogrammes, kilogram, kilograms",
                    "pound: pound, pounds, lb, lbs"
            )
        }
    }

    class RefuseAnswer : EnumEntity() {
        override fun getExamples(lang: Language): List<String> {
            return listOf( "I'd rather not say", "I don't want to say",
                    "I don't want to answer this", "Next question", "I don't want to give this information"
            )
        }
    }

    class DontKnow : Intent() {
        override fun getExamples(lang: Language): List<String> {
            return listOf(
                    "I don't know",
                    "don't know",
                    "not sure",
                    "who can say?",
                    "dunno",
                    "I dunno",
                    "I have no idea")
        }
    }

    class TellHeight : Intent() {
        var height: Number = Number(-1)
        override fun getExamples(lang: Language): List<String> {
            return listOf(
                    "@height",
                    "@height centimeters",
                    "@height cm",
                    "I'm @height",
                    "My height is @height",
                    "I'm @height centimeters tall"
            )
        }
    }

    class TellAllergies : Intent() {
        var allergy: Allergy? = null
        override fun getExamples(lang: Language): List<String> {
            return listOf("@allergy", "I have @allergy", "I suffer from @allergy", "I'm allergic to @allergy", "My allergy is @allergy")
        }
    }

    class Allergy : EnumEntity() {
        override fun getEnum(lang: Language): List<String> {
            return listOf(
                    "penicillin", "aspirin", "ibuprofen"
            )
        }
    }

}

