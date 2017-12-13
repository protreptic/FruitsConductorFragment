package example.fruits.data.remote.fruit

import example.fruits.data.model.Fruit
import io.reactivex.Single
import retrofit2.http.*

interface FruitsApi {

    /**
     * Возвращает список всех фруктов.
     */
    @GET("fruits")
    fun fruits(): Single<List<Fruit>>

    /**
     * Возвращает фрукт по его идентификатору.
     */
    @GET("fruit/{id}")
    fun fruit(@Path("id") id: Int?): Single<Fruit>

    /**
     * Добавляет фрукт.
     */
    @POST("fruit")
    fun addFruit(@Body fruit: Fruit): Single<Fruit>

    /**
     * Удаляет фрукт.
     */
    @DELETE("fruit/{id}")
    fun removeFruit(@Path("id") id: Int?): Single<Fruit>

}