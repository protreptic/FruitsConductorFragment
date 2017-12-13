package example.fruits.presentation.conductor.fruit

import android.os.Bundle
import android.view.View
import android.widget.TextView
import example.fruits.R
import example.fruits.data.model.Fruit
import example.fruits.data.repository.FruitsRepositoryImpl
import example.fruits.presentation.fruit.FruitPresenter.FruitView
import example.fruits.presentation.fruit.FruitPresenterImpl
import example.fruits.presentation.conductor.base.AbsController
import example.fruits.util.bindView

class FruitController: AbsController, FruitView {

    private var fruitId: Int = 0

    constructor(fruitId: Int): super(Bundle().apply { putInt("fruitId", fruitId) }) {
        this.fruitId = fruitId
    }

    @Suppress("unused")
    constructor(arguments: Bundle): super(arguments) {
        fruitId = arguments.getInt("fruitId")
    }

    override fun targetLayout(): Int = R.layout.fruit

    private val fruitPresenter by lazy {
        FruitPresenterImpl(this, FruitsRepositoryImpl())
    }

    override fun onAttach(view: View) {
        super.onAttach(view)

        fruitPresenter.attach()
        fruitPresenter.displayFruit(fruitId)
    }

    override fun onDetach(view: View) {
        fruitPresenter.detach()

        super.onDetach(view)
    }

    private val vName: TextView by bindView(R.id.name)

    override fun attach() {}

    override fun displayFruit(fruit: Fruit) {
        vName.text = fruit.name
    }

    override fun detach() {}

}
