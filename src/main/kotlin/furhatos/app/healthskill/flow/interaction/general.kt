package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.extractInt
import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.flow.introduction.giveSuggestion
import furhatos.app.healthskill.flow.phrases
import furhatos.app.healthskill.nlu.intents
import furhatos.app.healthskill.storePatient
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val furtherHelp = state{
    onEntry {
        furhat.ask("Do you need any further help?")
    }
    onResponse<Yes> {
        furhat.ask("How can I help you?")
    }
    onResponse<No> {
        if (users.current.Records.recalledPatient) {
            furhat.say("Ok, good luck with your recovery! Have a nice day!")
        } else {
            furhat.say("Good luck with your recovery! I will check up with you in the future to keep track of your recovery process.")
            furhat.say("Good bye, until next time!")
        }
    }

    onResponse<intents.forFurtherHelp> {
        furhat.ask(phrases.askForSymptoms)
    }
    onResponse<intents.TellSymptoms> {
        if (users.current.Records.showRecall) {
            if (users.current.Records.mainSymptom?.text.equals("headache")) {
                furhat.say("Understood, you have a ${it.intent.mainSymptoms}")
            } else {
                furhat.say("Understood, you have ${it.intent.mainSymptoms}")
            }
        }
        users.current.Records.mainSymptom = it.intent.mainSymptoms
        if (users.current.Records.showRecall) {
            goto(recallCheck)
        }
        goto(getPainDescription)
    }
}

val recallCheck = state{
    onEntry {
        val records  = users.current.Records
        if (users.current.Records.allergy == null) {
            furhat.say("Before we proceed, I'd like to confirm that you're a ${records.age} years old ${records.sex}. Make sure this information is correct as it will be used to give personalised medical suggestions")
        } else {
            furhat.say("Before we proceed, I'd like to confirm that you're a ${records.age} years old ${records.sex} and allergic to ${users.current.Records.allergy?.value}.  Make sure this information is correct as it will be used to give personalised medical suggestions")
        }
        furhat.ask("Do you want to change this information?")
    }
    onResponse<Yes> {
        furhat.say("Okay, cool.")
        users.current.Records.age = -1
        users.current.Records.sex = null
        users.current.Records.allergy = null
        goto(checkMissingEntries)
    }
    onResponse<No> {
        furhat.say("Cool.")
        goto(getPainDescription)
    }
}

val getDegree = state {
    onEntry {
        furhat.ask("What's your body temperature now?")
             }
    onResponse<intents.DontKnow> {
        furhat.ask(phrases.generalEstimate)
    }
    onResponse<intents.TellDegree> {
        val degree = extractInt(it.intent.degree?.value)
        users.current.Records.degree = degree.toDouble()
        if (users.current.Records.showRecall) {
            furhat.say("Ok your body temperature is ${degree} degrees.")
        }
        goto(askMinorSymptom)
    }
}


val askMinorSymptom = state {
    onEntry {
        random(
            {furhat.ask("Could you describe other symptoms you may have to me?")},
            {furhat.ask("Please describe any other symptoms you may have, so I can help you better.")},
            {furhat.ask("My recommendations can be more accurate if you describe some further symptoms. Could you tell me what else you're experiencing?")}
        )

    }
    onResponse<intents.TellMinor> {
        random(
            {furhat.say("Okay, give me some time to find the best recommendation for your case.")},
            {furhat.say("Thanks for the information, I will look into the best advice in a moment please.")}
        )
        users.current.Records.minorSymptom = it.intent.minor
        val suggestion =
            users.current.Records.mainSymptom?.let { it1 -> users.current.Records.minorSymptom?.let { it2 ->
                users.current.Records.painLevel?.let { it3 ->
                    giveSuggestion(it1,
                        it2, users.current
                    )
                }
            } }
        print("suggestion is $suggestion")
        furhat.say("$suggestion")
        if (!users.current.Records.recalledPatient) {
            val records = users.current.Records
            if (records.mainSymptom?.value == "dry ices") {
                records.mainSymptom?.value = "dry eyes"
            }
            if (records.minorSymptom?.value == "dizzy") {
                records.minorSymptom?.value = "dizziness"
            }
            var outputJson = "{\"name\":\"Santa\", \"age\":${records.age},\"weight\":${records.weight},\"height\":${records.height}," +
                    "\"physicalActivity\":${records.physicalActivity},\"mainSymptom\":\"${records.mainSymptom?.value}\"," +
                    "\"degree\":${records.degree},\"emotionLevel\":${records.emotionLevel},\"painLevel\":${records.painLevel}," +
                    "\"sex\":\"${records.sex?.value}\",\"minorSymptom\":\"${records.minorSymptom?.value}\",\"allergy\":\"${records.allergy?.value}\"}"
            println(outputJson)
            storePatient(outputJson)
        }
        furhat.say("I hope this suggestion helps you.")
        goto(furtherHelp)
    }
}


