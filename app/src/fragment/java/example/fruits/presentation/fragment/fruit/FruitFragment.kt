package example.fruits.presentation.fragment.fruit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.TextView
import example.fruits.R
import example.fruits.data.model.Fruit
import example.fruits.data.repository.FruitsRepositoryImpl
import example.fruits.presentation.fragment.base.AbsFragment
import example.fruits.presentation.fruit.FruitPresenter.FruitView
import example.fruits.presentation.fruit.FruitPresenterImpl
import kotterknife.bindView

class FruitFragment: AbsFragment(), FruitView {

    companion object {

        const val TAG = "FruitFragment"
        const val ARG_FRUIT = "ARG_FRUIT"

        fun newInstance(fruitId: Int): Fragment {
            return FruitFragment().apply {
                arguments = Bundle()
                arguments.putInt(ARG_FRUIT, fruitId)
            }
        }

    }

    override fun targetLayout(): Int = R.layout.fruit

    private val fruitPresenter by lazy {
        FruitPresenterImpl(this, FruitsRepositoryImpl())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fruitPresenter.attach()
        fruitPresenter.displayFruit(arguments.getInt(ARG_FRUIT))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fruitPresenter.detach()
    }

    private val vName: TextView by bindView(R.id.name)

    override fun attach() {}

    override fun displayFruit(fruit: Fruit) {
        vName.text = fruit.name
    }

    override fun detach() {}

}