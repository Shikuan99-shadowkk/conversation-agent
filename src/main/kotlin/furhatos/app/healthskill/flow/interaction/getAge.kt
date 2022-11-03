package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.extractInt
import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.*

val getAge : State = state{
    onEntry {
        furhat.ask ("How old are you now?")
    }
    onResponse<intents.TellAge> {
        if (users.current.Records.showRecall) {
            furhat.say("Great, you are ${it.intent.age} years old")
        }
        users.current.Records.age = extractInt(it.intent.age?.value)
        goto(checkMissingEntries)
    }

}
