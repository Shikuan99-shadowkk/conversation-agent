package furhatos.app.healthskill

import furhatos.app.healthskill.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class HealthSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}

//    var outputJson = "{\"name\":\"Sam\", \"age\":100,\"weight\":200,\"height\":170,\"physicalActivity\":false,\"mainSymptom\":\"back pain\"," +
//            "\"degree\":-1.0,\"emotionLevel\":0.2,\"painLevel\":9,\"sex\":\"female\",\"minorSymptom\":\"muscle ache\"," +
//            "\"allergy\":\"penicillin\"}"
//    storePatient(outputJson)
