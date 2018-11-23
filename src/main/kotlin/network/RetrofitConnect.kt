package network

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.InetSocketAddress
import java.net.Proxy


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
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("web-proxy.corp.hp.com", 8080))
            val client = OkHttpClient.Builder().proxy(proxy).build()
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



