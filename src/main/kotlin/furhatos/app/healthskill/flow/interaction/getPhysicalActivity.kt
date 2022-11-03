package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.flow.Records
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val getPhysicalActivity : State = state {
    onEntry {
        furhat.ask("Would you say you get around 30 minutes of physical activity per day?")
    }
    onResponse<Yes> {
        users.current.Records.physicalActivity = true
        users.current.Records.checkPhysicalActivity = false
        if (users.current.Records.showRecall) {
            furhat.say("That's great.")
        }
        goto(checkMissingEntries)
    }
    onResponse<No> {
        users.current.Records.physicalActivity = false
        users.current.Records.checkPhysicalActivity = false
        goto(checkMissingEntries)
    }

}
