package furhatos.app.healthskill.flow

import furhatos.app.healthskill.nlu.intents
import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.nlu.common.Number
import furhatos.records.User


class Patient {
    var painLevel : Int = -1
    var age: Int = -1
    var sex: intents.Sex? = null
    var weight : Int = -1
    var height : Int = -1
    var checkPhysicalActivity = true
    var physicalActivity = false
    var mainSymptom : intents.MainSymptom? = null
    var minorSymptom : intents.MinorSymptom? = null
    var degree : Double = -1.0
    var allergy : intents.Allergy? = null
    var showRecall = true
    var emotionLevel : Double = -1.0
    var recalledPatient = false
}

class PatientJson {
    var painLevel : Int = -1
    var age: Int = -1
    var sex: String = ""
    var weight : Int = -1
    var height : Int = -1
    var physicalActivity = false
    var mainSymptom : String = ""
    var minorSymptom : String = ""
    var degree : Double = -1.0
    var allergy : String = ""
    var emotionLevel : Double = -1.0
}

val User.Records by NullSafeUserDataDelegate{ Patient() }
