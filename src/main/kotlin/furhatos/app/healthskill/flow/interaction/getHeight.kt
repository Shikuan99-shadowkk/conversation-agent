package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.extractInt
import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.flow.phrases
import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.*

val getHeight : State = state{
    onEntry {
        furhat.ask ("How tall are you?")
    }
    onResponse<intents.DontKnow> {
        furhat.ask(phrases.generalEstimate)
    }
    onResponse<intents.TellHeight> {
        val height = it.intent.height
        users.current.Records.height = extractInt(height?.value)
        goto(checkMissingEntries)
    }
}
