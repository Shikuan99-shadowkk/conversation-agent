package furhatos.app.healthskill

import com.google.gson.Gson
import furhatos.app.healthskill.flow.PatientJson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*

const val port = 8081;
const val host = "localhost";

fun getRandomString(list: List<String>) : String {
    return list[Random().nextInt(list.size)]
}

fun getPolarity(inputText: String): Double {
    val client = Socket(host, port)
    val outputStream = PrintWriter(client.getOutputStream(), true)
    println("Client sending: $inputText")
    outputStream.println("polarity#$inputText")
    val inputStream = BufferedReader(InputStreamReader(client.inputStream))
    val outputVal: Double = inputStream.readLine().toDouble()
    println("Client receiving: $outputVal")
    outputStream.println("close")
    client.close()
    return outputVal
}

fun storePatient(inputText: String) {
    val client = Socket(host, port)
    val outputStream = PrintWriter(client.getOutputStream(), true)
    println("Client sending: $inputText")
    outputStream.println("storePatient#$inputText")
    val inputStream = BufferedReader(InputStreamReader(client.inputStream))
    inputStream.readLine()
    outputStream.println("close")
    client.close()
}

fun getPatient(): PatientJson? {
    val client = Socket(host, port)
    val outputStream = PrintWriter(client.getOutputStream(), true)
    println("Client sending: getPatient")
    outputStream.println("getPatient#getPatient")
    val inputStream = BufferedReader(InputStreamReader(client.inputStream))
    val outputJson = inputStream.readLine()
    var patient : PatientJson? = null
    println("Client receiving: $outputJson")
    if (outputJson != "no patient") {
        patient = Gson().fromJson(outputJson, PatientJson::class.java)
    }
    outputStream.println("close")
    client.close()
    return patient
}

fun extractInt(num: Int?): Int {
    return when {
        num != null -> num
        else -> throw Error()
    }
}


