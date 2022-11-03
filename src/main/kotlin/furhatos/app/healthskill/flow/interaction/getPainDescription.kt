package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.getPolarity
import furhatos.flow.kotlin.*

val getPainDescription : State = state {
    onEntry {
        if (users.current.Records.recalledPatient) {

            furhat.ask("Please give a closer description of the level of pain you're experiencing.")
        } else {
            furhat.ask("In order to assess the severity of your illness, please describe more closely the level of pain you're feeling.")
        }
    }
    onReentry {
        furhat.ask("Please describe more closely the level of pain you're feeling.")
    }
    onResponse {
        val emotionLevel = getPolarity(it.text)
        users.current.Records.emotionLevel = emotionLevel
//        furhat.say("Your emotion level is ${emotionLevel}")
        print("intent it $emotionLevel")
        goto(getPainLevel)
    }

}
