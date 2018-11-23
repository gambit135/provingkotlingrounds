package okhttp

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get


fun main(args: Array<String>) {
    println("Hello OkHTTP")
    val client = HttpClient(OkHttp) {
        engine {
            // https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.Builder.html
            config {
                // this: OkHttpClient.Builder ->
                // ...
                followRedirects(true)
                // ...
            }

            // https://square.github.io/okhttp/3.x/okhttp/okhttp3/Interceptor.html
            //addInterceptor(interceptor)
            // addNetworkInterceptor(interceptor)

        }

    }


    val htmlContent = client.get<String>("https://en.wikipedia.org/wiki/Main_Page")


}
