package hitEndpoint

import coroutines.coroutines5
import kotlinx.coroutines.*
import kotlinx.coroutines.time.delay
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection


internal var URLSTRING = "https://wordpress.org:443/support/topic/page-jumps-within-wordpress/?replies=3#post-2278484"
// parsed locator
internal var URLPROTOCOL = "https"
// final static String URLAUTHORITY = "wordpress.org:443";
internal var URLHOST = "wordpress.org"
internal var URLPATH = "/support/topic/page-jumps-within-wordpress/"
// final static String URLFILENAME = "/support/topic/page-jumps-within-wordpress/?replies=3";
// final static int URLPORT = 443;
internal var URLDEFAULTPORT = 443
internal var URLQUERY = "replies=3"
internal var URLREFERENCE = "post-2278484"
internal var URLCOMPOUND = URLPROTOCOL + "://" + URLHOST + ":" + URLDEFAULTPORT + URLPATH + "?" + URLQUERY + "#" + URLREFERENCE

var testBearerUrl = "http://api-lodging-taxes-v2-assembly-test.us-east-1-vpc-88394aef.slb-internal.test.aws.away.black/v2/unitLodgingTaxes/getByUnit?unitUrl=/units/0004/5995a7da-a647-4932-99ee-d785640fa66f"

fun main(args: Array<String>) {
    //hitUrl("http://www.google.com/")
    coroutines5(testBearerUrl)
}

fun hitUrl(urlString: String): String {

    var url: URL? = null
    var urlConnection: URLConnection? = null
    var connection: HttpURLConnection? = null
    var brIn: BufferedReader? = null
    var urlStringCont = ""

    url = URL(urlString)
    // get URL connection
    urlConnection = url.openConnection()
    connection = null


    // we can check, if connection is proper type
    if (urlConnection is HttpURLConnection) {
        connection = urlConnection
    } else {
        println("Please enter an HTTP URL")
        throw IOException("HTTP URL is not correct")
    }
    // we can check response code (200 OK is expected)
    println(connection.responseCode.toString() + " " + connection.responseMessage)
    brIn = BufferedReader(InputStreamReader(connection.inputStream))
    var current: String = ""

    while (current != null) {
        current = brIn.readLine()
        urlStringCont += current
    }
    return urlStringCont
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