package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.flow.introduction.Idle
import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.flow.phrases
import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val getSymptoms = state{
    onEntry{
        val records = users.current.Records
        if (records.mainSymptom != null){
            goto(recallSymptoms)
        }else {
            furhat.ask(phrases.askForSymptoms)
        }

    }
    onReentry {
        furhat.ask(phrases.askForSymptoms)
    }
    onResponse<Yes> {
        random(
                { furhat.ask("Okay, don't worry. Let me help you. What are some symptoms you are experiencing?") },
                { furhat.ask("Okay, don't worry. Please describe your symptoms to me, so I can make a diagnosis.") }
        )
    }

    onResponse<No> {
        furhat.say("I am glad to hear that. Have a splendid day!")
        goto(Idle)
    }
    onResponse<intents.TellSymptoms> {
        val mainsymp = it.intent.mainSymptoms
        users.current.Records.mainSymptom = mainsymp
        if (users.current.Records.showRecall) {
            if (users.current.Records.mainSymptom?.text.equals("headache")) {
                furhat.say("Understood, you have a ${mainsymp}")
            } else {
                furhat.say("Understood, you have ${mainsymp}")
            }
        }
        furhat.say("Before proceeding with a recommendation, we will need to go over some missing information.")
        goto(checkMissingEntries)
    }


}
