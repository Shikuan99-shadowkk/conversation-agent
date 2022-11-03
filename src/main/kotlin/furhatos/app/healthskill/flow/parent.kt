package furhatos.app.healthskill.flow

import furhatos.app.healthskill.flow.introduction.Greeting
import furhatos.app.healthskill.flow.introduction.Idle
import furhatos.flow.kotlin.*

val Parent: State = state {

    onUserLeave(instant = true) {
        when {
            users.count == 0 -> goto(Idle)
            it == users.current -> {
                furhat.attend(users.other)
                goto(Greeting)
            }
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }
}
