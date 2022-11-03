package furhatos.app.healthskill.flow.introduction

import furhatos.app.healthskill.flow.Parent
import furhatos.app.healthskill.flow.PatientJson
import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.flow.interaction.getSymptoms
import furhatos.app.healthskill.flow.interaction.recallSymptoms
import furhatos.app.healthskill.flow.phrases
import furhatos.app.healthskill.getPatient
import furhatos.app.healthskill.nlu.intents.*
import furhatos.flow.kotlin.*

val Greeting : State = state(Parent) {
    onEntry {
        val patientJson: PatientJson? = getPatient()
        if (patientJson == null) {
            furhat.say(phrases.greeting)
            goto(getSymptoms)
        } else {
            furhat.say(phrases.reGreeting)
            users.current.Records.painLevel = patientJson.painLevel
            users.current.Records.age = patientJson.age
            var sex = Sex()
            sex.value = patientJson.sex
            users.current.Records.sex = sex
            users.current.Records.weight = patientJson.weight
            users.current.Records.height = patientJson.height
            users.current.Records.physicalActivity = patientJson.physicalActivity
            var mainSymptom = MainSymptom()
            mainSymptom.value = patientJson.mainSymptom
            users.current.Records.mainSymptom = mainSymptom
            var minorSymptom = MinorSymptom()
            minorSymptom.value = patientJson.minorSymptom
            users.current.Records.minorSymptom = minorSymptom
            users.current.Records.degree = patientJson.degree
            var allergy = Allergy()
            allergy.value = patientJson.allergy
            users.current.Records.allergy = allergy
            users.current.Records.emotionLevel = patientJson.emotionLevel
            users.current.Records.recalledPatient = true
            goto(recallSymptoms)
        }
    }
}
