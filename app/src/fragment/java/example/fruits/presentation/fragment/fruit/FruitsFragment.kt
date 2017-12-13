package example.fruits.presentation.fragment.fruit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import example.fruits.R
import example.fruits.data.model.Fruit
import example.fruits.data.remote.fruit.FruitsApiProvider.fruitsApi
import example.fruits.data.repository.FruitsRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import example.fruits.presentation.fragment.base.AbsFragment
import example.fruits.presentation.fruit.FruitsAdapter
import example.fruits.presentation.fruit.FruitsPresenter.FruitsView
import example.fruits.presentation.fruit.FruitsPresenterImpl
import kotterknife.bindView
import java.util.concurrent.TimeUnit

class FruitsFragment: AbsFragment(), FruitsView {

    companion object {

        const val TAG = "FruitsFragment"

        fun newInstance(): Fragment {
            return FruitsFragment().apply {
                arguments = Bundle.EMPTY
            }
        }

    }

    override fun targetLayout(): Int = R.layout.fruits

    private val fruitsPresenter by lazy {
        FruitsPresenterImpl(this, FruitsRepositoryImpl(), fruitsApi())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fruitsPresenter.attach()

        if (savedInstanceState == null) {
            fruitsPresenter.displayFruits()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fruitsPresenter.detach()
    }

    private val vLoading: ProgressBar by bindView(R.id.vLoading)
    private val vEmpty: TextView by bindView(R.id.vEmpty)
    private val vFilter: EditText by bindView(R.id.vFilter)
    private val vFruits: RecyclerView by bindView(R.id.vFruits)

    override fun attach() {
        vFruits.layoutManager = LinearLayoutManager(context)

        RxTextView.afterTextChangeEvents(vFilter)
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map { t: TextViewAfterTextChangeEvent -> t.editable().toString() }
                .subscribe({ t: String -> fruitsPresenter.filterFruits(t) })
    }

    override fun displayFruits(fruits : List<Fruit>) {
        vLoading.visibility = View.GONE
        vEmpty.visibility = View.GONE
        vFilter.visibility = View.VISIBLE
        vFruits.visibility = View.VISIBLE
        vFruits.adapter = FruitsAdapter(fruits).apply {
            setItemClickListener(object: FruitsAdapter.OnClickListener {

                override fun onClick(fruit: Fruit) {
                    fragmentManager.beginTransaction()
                            .replace(android.R.id.content,
                                    FruitFragment.newInstance(fruit.id), FruitFragment.TAG)
                            .addToBackStack(FruitFragment.TAG)
                            .commit()
                }

            })
        }
    }

    override fun displayLoading() {
        vLoading.visibility = View.VISIBLE
        vEmpty.visibility = View.GONE
        vFilter.visibility = View.GONE
        vFruits.visibility = View.GONE
    }

    override fun displayEmpty() {
        vLoading.visibility = View.GONE
        vEmpty.visibility = View.VISIBLE
        vFilter.visibility = View.VISIBLE
        vFruits.visibility = View.GONE
    }

    override fun displayError() {
        Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
    }

    override fun detach() {
        vFruits.adapter = null
    }

}