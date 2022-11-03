import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.flow.interaction.getPainDescription
import furhatos.app.healthskill.flow.interaction.getPainLevel
import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val getAllergies = state {
    onEntry{
        furhat.ask("Are you aware of any drug related allergies you might have?")
    }
    onReentry {
        furhat.ask("Do you have an allergic reactions to certain drugs?")
    }
    onResponse<No> {
        if (users.current.Records.showRecall) {
            random(
                    { furhat.say("Good to hear") },
                    { furhat.say("That's great") })
        }
        goto(getPainDescription)
    }

    onResponse<Yes> {
        furhat.ask("Please name these drugs")
    }
    onResponse<intents.TellAllergies> {
        if (users.current.Records.showRecall) {
            furhat.say("I will keep your allergic reaction to ${it.intent.allergy} in mind when making recommendations.")
        }
        users.current.Records.allergy = it.intent.allergy
        goto(getPainDescription)
    }
}
