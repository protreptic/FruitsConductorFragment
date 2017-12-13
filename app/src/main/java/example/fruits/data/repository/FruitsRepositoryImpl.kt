package example.fruits.data.repository

import example.fruits.data.model.Fruit
import example.fruits.data.model.Fruit.Companion.F_ID
import io.reactivex.Single
import io.realm.Realm

class FruitsRepositoryImpl : FruitsRepository {

    override fun retain(updatedFruits: List<Fruit>) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.copyToRealmOrUpdate(updatedFruits)
            }
        }
    }

    override fun fetch(): List<Fruit> {
        Realm.getDefaultInstance().use {
            return it.copyFromRealm(
                    it.where(Fruit::class.java)
                            .findAll())
        }
    }

    override fun fetchRx(): Single<List<Fruit>> =
            Single.just(fetch())

    override fun fetchById(id: Int): Fruit {
        Realm.getDefaultInstance().use {
            var foundFruit = it.where(Fruit::class.java)
                    .equalTo(F_ID, id)
                    .findFirst()

            if (foundFruit == null) {
                foundFruit = Fruit()
            }

            return it.copyFromRealm(foundFruit)
        }
    }

    override fun fetchByIdRx(id: Int): Single<Fruit> =
            Single.just(fetchById(id))

    override fun fetchByName(name: String): List<Fruit> =
            fetch().filter { it.name.contains(name, true) }

    override fun fetchByNameRx(name: String): Single<List<Fruit>> =
            Single.just(fetchByName(name))

}