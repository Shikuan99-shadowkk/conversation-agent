package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.extractInt
import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.flow.phrases
import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.*

val getWeight : State = state{
    onEntry {
        furhat.ask ("How much do you weigh?")
    }
    onResponse<intents.DontKnow> {
        furhat.ask(phrases.generalEstimate)
    }
    onResponse<intents.TellWeight> {
        val weight = it.intent.weight
        users.current.Records.weight = extractInt(weight?.value)
        goto(checkMissingEntries)
    }
}
