package example.fruits.presentation.fruit

import example.fruits.data.repository.FruitsRepository
import example.fruits.presentation.fruit.FruitPresenter.FruitView

class FruitPresenterImpl(private val view: FruitView,
                         private val repository: FruitsRepository): FruitPresenter {

    override fun attach() {
        view.attach()
    }

    override fun displayFruit(fruitId: Int) {
        repository
                .fetchByIdRx(fruitId)
                .subscribe { fruit ->
                    view.displayFruit(fruit)
                }
    }

    override fun detach() {
        view.detach()
    }

}