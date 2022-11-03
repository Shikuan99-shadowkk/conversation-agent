package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.flow.phrases
import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.*
import furhatos.nlu.Interpreter
import furhatos.util.Language
import furhatos.app.healthskill.nlu.intents.Sex

val getSex : State = state{
    onEntry {
        furhat.ask("What is your sex?")
    }
    onResponse<intents.RefuseAnswer> {
        furhat.say(phrases.refuseAnswer)
        val interp = Interpreter(Language.ENGLISH_US)
        users.current.Records.sex = interp.findFirst(Sex(), "other")
        goto(checkMissingEntries)
    }
    onResponse<intents.TellSex> {
        users.current.Records.sex = it.intent.sex
        goto(checkMissingEntries)
    }
}
