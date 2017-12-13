package example.fruits.domain.fruit

import example.fruits.data.model.Fruit
import example.fruits.data.remote.fruit.FruitsApi
import example.fruits.data.repository.FruitsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddFruitImpl(private val repository: FruitsRepository,
                   private val fruitsApi: FruitsApi) : AddFruit {

    override fun add(fruit: Fruit) {
        fruitsApi.addFruit(fruit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    repository.retain(listOf(it))
                }, {

                })
    }

}