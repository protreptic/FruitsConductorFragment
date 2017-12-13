package example.fruits.data.repository

import io.reactivex.Single
import example.fruits.data.model.Fruit

interface FruitsRepository {

    /**
     * Сохраняет список фруктов.
     */
    fun retain(updatedFruits: List<Fruit>)

    /**
     * Выбирает список всех фруктов.
     *
     * @return список фруктов
     */
    fun fetch(): List<Fruit>
    fun fetchRx(): Single<List<Fruit>>

    /**
     * Выбирает фрукт по его идентификатору.
     */
    fun fetchById(id: Int): Fruit
    fun fetchByIdRx(id: Int): Single<Fruit>

    /**
     * Выбирает список фруктов в названии которых
     * встречается указанный в параметрах текст.
     *
     * @param name название фрукта
     * @return список фруктов
     */
    fun fetchByName(name: String): List<Fruit>
    fun fetchByNameRx(name: String): Single<List<Fruit>>

}