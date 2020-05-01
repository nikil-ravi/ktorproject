/*

import io.ktor.http.Url
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Observable

const val url: String = "your url"

private val Url.Companion.BASE_URL: String
    get() {
        return BASE_URL
        TODO("Not yet implemented")
    }

private val Url.Companion.URL: String
    get() {
        return URL
        TODO("Not yet implemented")
    }


class Url {
    companion object {
        const val BASE_URL = "your base url"
        const val URL = "your url"
    }
}

class Connect {

    companion object {

        private fun getRetrofit(Url: String): Retrofit {
            return Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(Url)
                .build()
        }

        fun getApiData():Retrofit {
            val retrofitApi = getRetrofit(Url.BASE_URL)
            return retrofitApi
        }

        fun callApi():CallApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(CallApi::class.java)
        }

    }
}

object Model {
    data class Result(val data: Data)
    data class Data(val children: List<Children>)
    data class Children(val data: Datas)
    data class Datas(val author: String,val thumbnail: String,val title: String)
}

interface CallApi {

    @GET(url)
    //query needed if there is any query
    fun getApi(@Query("limit") limit: Int): Observable<Model.Result>
}

class ApiData {
    companion object {
        const val count = 10
        val api by lazy { Connect.callApi() }
        var disposable: Disposable? = null
        fun apiData(callback: Response) {
            disposable = api.getApi(count)
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback.data(result, true)
                }, { error ->
                    error.printStackTrace()
                })
        }
    }

    interface Response {
        fun data(data: Model.Result, status: Boolean)
    }
}

*/
