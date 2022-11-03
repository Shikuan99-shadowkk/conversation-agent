package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.flow.Records
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val recallSymptoms = state{
    onEntry{
        if (users.current.Records.showRecall) {
            furhat.ask("Hope you're feeling better. Are you still experiencing ${users.current.Records.mainSymptom}?")
        } else {
            furhat.ask("Hope you're feeling better. Are you still feeling ill?")
        }

    }
    onResponse<Yes> {
        furhat.say("Okay, don't worry. Please continue following my advice. " +
                "If this continues to persist for a longer duration I recommend a visit to the doctor.")
        goto(furtherHelp)
    }

    onResponse<No> {
        furhat.say("I am glad to hear that.")
        goto(furtherHelp)
    }
}
