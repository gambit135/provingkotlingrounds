package network

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.Executors


object Model {
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
}


interface WikiApiService {

    @GET("api.php")
    fun hitCountCheck(@Query("action") action: String,
                      @Query("format") format: String,
                      @Query("list") list: String,
                      @Query("srsearch") srsearch: String):
            Observable<Model.Result>

    companion object {
        fun create(): WikiApiService {
            println("on create")
            //val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("web-proxy.corp.hp.com", 8080))
           // val client = OkHttpClient.Builder().proxy(proxy).build()
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().client(client)
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://en.wikipedia.org/w/")
                    .build()

            return retrofit.create(WikiApiService::class.java)
        }

    }
}

var disposable: Disposable? = null
val wikiApiServe by lazy {
    println("Lazy wiki")
    WikiApiService.create()
}
private fun beginSearch(srsearch: String) {
    disposable =
            wikiApiServe.hitCountCheck("query", "json", "search", srsearch)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                    .subscribe(
                            { result -> println("Result: ${result.query.searchinfo.totalhits}") },
                            { error -> println("Error: ${error.message}") }
                    )
}

fun main(args: Array<String>) {
    println("hola")
    beginSearch("Black Sabbath")

}


