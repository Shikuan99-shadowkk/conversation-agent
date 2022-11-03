package furhatos.app.healthskill.flow

import furhatos.app.healthskill.flow.introduction.Idle
import furhatos.app.healthskill.setting.distanceToEngage
import furhatos.app.healthskill.setting.maxNumberOfUsers
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Aria-Neural")
        /** start the interaction */
        goto(Idle)
    }
}
