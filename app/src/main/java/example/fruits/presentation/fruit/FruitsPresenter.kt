package example.fruits.presentation.fruit

import example.fruits.data.model.Fruit
import example.fruits.presentation.base.Presenter
import example.fruits.presentation.base.View

interface FruitsPresenter: Presenter {

    /**
     *
     */
    fun displayFruits()

    /**
     *
     */
    fun filterFruits(name: String)

    /**
     *
     */
    interface FruitsView: View {

        /**
         *
         */
        fun displayFruits(fruits : List<Fruit>)

        /**
         *
         */
        fun displayLoading()

        /**
         *
         */
        fun displayEmpty()

        /**
         *
         */
        fun displayError()

    }

}