package coroutines

import kotlinx.coroutines.*
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients

fun main(args: Array<String>) {
    //hitUrl("http://www.google.com/")
    coroutines5("Foo")
}
@Throws(Exception::class)
private fun coroutines1(urlToCall: String) {
    val bearer = "OWZlODM5ZGQtYjNiOC00MzI2LWJiNGMtNDljMTlhMjViMmEw"
    val updateRequest = HttpGet(urlToCall)
    // updateRequest.entity = StringEntity("true")
    updateRequest.addHeader("Content-Type", "application/json")
    //This bearer must have the required roles to perform the patch operation in alt
    updateRequest.setHeader("Authorization", "Bearer $bearer")

    val httpClient = HttpClients.createDefault()
    val response = httpClient.execute(updateRequest)
    if (response.statusLine.statusCode !== 200) {
        //log.error("Error code was {} and message {}", response.statusLine.statusCode, response.statusLine.reasonPhrase)
        //throw HTTPException(response.statusLine.statusCode)
        println(response.statusLine.statusCode)
    } else {
        println(response.toString())
    }
}

fun coroutines2(a: String) = runBlocking {
    //(1)
    val job = /*GlobalScope.*/launch {
        //(2)
        val result = suspendingFunction(a) //(3)
        print("$result")
    }
    print("The result: ")
    // job.join() //(4)
}

fun suspendingFunction(s: String): Int {
    //println(s)
    var a = 3
    a += 3
    return a
}

fun coroutines3(a: String) {
    runBlocking {
        launch {
            delay(500)
            println("Hello from launch!")
        }
        println("Hello from runBlocking after launch!")
    }
    println("Finished runBlocking")
}

fun coroutines4(a: String) {
    runBlocking {
        var outerLaunch: Job? = null
        outerLaunch = launch {
            launch {
                while (true) {
                    delay(100)
                    println("Hello from first inner launch!")
                }
            }
            launch {
                while (true) {
                    try {
                        delay(600)
                        println("Hello from second inner launch")
                        throw Exception("Failed! D;")
                    } catch (e: Exception) {
                        println(e.localizedMessage)
                        outerLaunch?.cancel()
                        break
                    }
                }
            }
            println("Hello from runBlocking after outerLaunch")
            delay(3000)
        }
    }
    println("Finished runBlocking")
}

fun coroutines5(a: String) {
    runBlocking {
        coroutineScope {
            val outerLaunch = launch {
                launch {
                    while (true) {
                        delay(300)
                        println("Hello from 1st inner launch!")
                    }
                }
                launch {
                    while (true) {
                        delay(600)
                        println("Hello from 2nd inner launch! D:")
                    }
                }
            }
            println("Hello from runBlocking after outerLaunch!")
            delay(3000)
            outerLaunch.cancel()
        }
        println("Finished coroutine scope!")
    }
}