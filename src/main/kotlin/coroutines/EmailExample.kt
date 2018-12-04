package coroutines

import kotlinx.coroutines.*

suspend fun sendEmail(recipient: String, message: String): Boolean {
    delay(2000)
    println("Sent $message to $recipient")
    return true
}

suspend fun getReceiverAddressFromDB(): String {
    delay(400)
    val address = "coroutine@kotlin.org"
    println("Retrieved address: $address")
    return address
}

suspend fun sendEmailSuspending(): Boolean {
    val msg = GlobalScope.async {
        delay(300)
        "Message content"
    }

    val rcp = GlobalScope.async {
        getReceiverAddressFromDB()
    }

    println("Waiting for email data")

    val sendStatus = GlobalScope.async {
        sendEmail(rcp.await(), msg.await())
    }
    return sendStatus.await()
}

fun main(args: Array<String>) = runBlocking {
    val job = GlobalScope.launch {
        sendEmailSuspending()
        println("Email sent successfully")
    }
    job.join()
    println("Finished")
}