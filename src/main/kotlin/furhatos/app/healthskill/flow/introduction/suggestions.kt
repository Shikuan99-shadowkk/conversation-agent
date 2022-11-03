package furhatos.app.healthskill.flow.introduction

import furhatos.app.healthskill.flow.Records
import furhatos.app.healthskill.nlu.intents
import furhatos.records.User

fun giveSuggestion(main: intents.MainSymptom, minor: intents.MinorSymptom, user: User): String {
    val painLevel = user.Records.painLevel

//    if (main.text.equals("headache")){
//        if (minor.text.equals("cough")){
//            if (pain >= Number(0) && pain <= Number(6)){
//                return "I recommend you take 200mg of Ibuprofen and drink some water."
//            }
//        }
//    }else if (main.text.equals("vomiting")){
//        if (minor.text.equals("stomachache") || minor.text.equals("stomach ache")){
//            if (pain >= furhatos.nlu.common.Number(0) && pain <= furhatos.nlu.common.Number(6)){
//                return "I would suggest you take Antihistamines pills 2 times a day, you can easily buy it in medic shop"
//            }
//            if (pain >= furhatos.nlu.common.Number(7)){
//                return "Based on your condition, I would suggest you go to see a doctor as fast as possible"
//            }
//        }
//    }
    if (main.text.equals("fever")){
        if(minor.text.equals("sore throat")){
            return if (user.Records.allergy?.text.equals("ibuprofen")  && user.Records.showRecall) {
                "The usual recommendation for those symptoms would be 200mg of Ibuprofen. However, considering your allergies I suggest you take 500 mg of Paracetamol instead." +
                        " In the meantime, some hot water with lemon can help with your sore throat."
            } else {
                "I would suggest you take 500mg of Paracetamol two times a day and drink some hot water with lemon in the meantime."

            }
        }
//    } else if(main.text.equals("diarrhea")){
//        if(minor.text.equals("dehydration")){
//            if (pain >= furhatos.nlu.common.Number(0) && pain <= furhatos.nlu.common.Number(6)){
//                return "I would suggest you take Imodium Smelt tablet Orodisp Tablet 2mg version 3 times a day. In the meanwhile, I would recommend you drink some hot water with lemon, it would help with your dehydration"
//            }
//            if (pain >= furhatos.nlu.common.Number(7)){
//                return "I would suggest you go to see a doctor as fast as possible"
//            }
//        }
    } else if(main.text.equals("dry eyes") || main.text.equals("dry ices")){
        if(minor.text.equals("dizziness") || minor.text.equals("dizzy")){
            return if (user.Records.showRecall) {
                if (painLevel in 0..6) {
                    "I suggest using eye drops to treat your dry eyes. The recommended dosage is the 2ml version applied three times a day."
                } else {
                    "Your dry eyes and dizziness seem to indicate a more serious illness. I suggest you go see a doctor as fast as possible."
                }
            } else {
                if (painLevel in 0..6) {
                    "I suggest using eye drops for three times a day."
                } else {
                    "Your symptoms seem to indicate a more serious illness. I suggest you go see a doctor as fast as possible."
                }
            }
        }
    } else if(main.text.equals("back pain")){
        if (minor.text.equals("muscle ache") || minor.text.equals("muscle aches")){
            if (user.Records.showRecall) {
                return if (user.Records.physicalActivity) {
                    if (painLevel in 0..6) {
                        "I suggest you take 325mg of Aspirin and apply a warm compress to the back."
                    } else {
                        "Your back pain and muscle ache seem to indicate a more serious illness. I suggest you go see a doctor as fast as possible."
                    }
                } else {
                    if (painLevel in 0..6) {
                        "I recommend following some abdominal and back muscle exercises regularly to reduce the symptoms. In the meantime, you can take 325mg of Aspirin to relieve the pain."
                    } else {
                        "Your back pain and muscle ache seem to indicate a more serious illness. I suggest you go see a doctor as fast as possible."
                    }
                }
            } else {
                return if (painLevel in 0..6) {
                    "I suggest you take 325mg of Aspirin and use a warm compress."
                } else {
                    "Your symptoms seem to indicate a more serious illness. I suggest you go see a doctor as fast as possible."
                }
            }
        }
    }
    return "Take Medicine"
}
