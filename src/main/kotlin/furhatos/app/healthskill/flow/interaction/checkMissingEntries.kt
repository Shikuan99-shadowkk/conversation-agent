package furhatos.app.healthskill.flow.interaction

import furhatos.app.healthskill.flow.Records
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import getAllergies

val checkMissingEntries  = state {
    onEntry {
        val records = users.current.Records
        print(records)
        when{
            records.age == -1 -> goto(getAge)
            records.sex == null -> goto(getSex)
            records.weight == -1 -> goto(getWeight)
            records.height == -1 -> goto(getHeight)
            records.checkPhysicalActivity -> goto(getPhysicalActivity)
            records.allergy == null -> goto(getAllergies)
            records.painLevel == -1  -> goto(getPainDescription)
        }
    }
}
