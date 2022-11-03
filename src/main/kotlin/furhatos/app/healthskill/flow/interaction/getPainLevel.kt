package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.extractInt
import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.*

val getPainLevel : State = state {
    onEntry {
        if (users.current.Records.showRecall && !users.current.Records.mainSymptom?.text.equals("dry eyes")
                && !users.current.Records.mainSymptom?.text.equals("dry ices")) {
            furhat.ask("On a scale of 1 to 10, how strong do you think your ${users.current.Records.mainSymptom} is?")
        } else {
            furhat.ask("On a scale of 1 to 10, how would you rate your pain?")
        }
    }
    onReentry {
        furhat.ask("How would you rate the pain you are feeling right now?")
    }
    onResponse<intents.TellPainlevel> {
        val extractedPainLevel = extractInt(it.intent.painLevel?.value)
        if (users.current.Records.showRecall){
            if (extractedPainLevel <= 3) {
                furhat.say("It seems like you're experiencing light pain.")
            }
            else if (extractedPainLevel in 4..6) {
                furhat.say("It seems like you're experiencing moderate pain.")
            }
            else {
                furhat.say("It seems like you're experiencing heavy pain.")
            }
        }
        furhat.say("Okay, I need a bit more information to give you advice.")
        users.current.Records.painLevel = extractedPainLevel
        if (users.current.Records.mainSymptom?.text.equals("fever")) {
            goto(getDegree)
        } else {
            goto(askMinorSymptom)
        }
    }

}
