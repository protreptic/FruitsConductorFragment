package example.fruits.presentation.fruit

import example.fruits.data.model.Fruit
import example.fruits.presentation.base.Presenter
import example.fruits.presentation.base.View

interface FruitPresenter: Presenter {

    /**
     *
     */
    fun displayFruit(fruitId: Int)

    /**
     *
     */
    interface FruitView: View {

        /**
         *
         */
        fun displayFruit(fruit: Fruit)

    }

}