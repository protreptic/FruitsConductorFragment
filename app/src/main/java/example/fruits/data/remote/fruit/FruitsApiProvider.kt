package example.fruits.data.remote.fruit

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

object FruitsApiProvider {

    private var init: Boolean = false
    private var instance: FruitsApi? = null

    private val lock = Object()

    @JvmStatic
    fun initApi(context: Context) {
        synchronized(lock) {
            Retrofit.Builder()
                    .baseUrl(FruitsUrlProvider(context).provide())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FruitsApi::class.java)
                    .also {
                        init = true
                        instance = it }
        }
    }

    @JvmStatic
    fun fruitsApi() : FruitsApi {
        synchronized(lock) {
            if (init) {
                return instance!!
            } else {
                throw RuntimeException("Initialize API first")
            }
        }
    }

}