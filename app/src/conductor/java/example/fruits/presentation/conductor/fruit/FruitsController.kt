package example.fruits.presentation.conductor.fruit

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import example.fruits.R
import example.fruits.data.model.Fruit
import example.fruits.data.remote.fruit.FruitsApiProvider.fruitsApi
import example.fruits.data.repository.FruitsRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import example.fruits.presentation.conductor.base.AbsController
import example.fruits.presentation.fruit.FruitsAdapter
import example.fruits.presentation.fruit.FruitsPresenter.FruitsView
import example.fruits.presentation.fruit.FruitsPresenterImpl
import example.fruits.util.bindView
import java.util.concurrent.TimeUnit

class FruitsController: AbsController(), FruitsView {

    override fun targetLayout(): Int = R.layout.fruits

    private val fruitsPresenter by lazy {
        FruitsPresenterImpl(this, FruitsRepositoryImpl(), fruitsApi())
    }

    override fun onAttach(view: View) {
        super.onAttach(view)

        fruitsPresenter.attach()
        fruitsPresenter.displayFruits()
    }

    override fun onDetach(view: View) {
        fruitsPresenter.detach()

        super.onDetach(view)
    }

    private val vLoading: ProgressBar by bindView(R.id.vLoading)
    private val vEmpty: TextView by bindView(R.id.vEmpty)
    private val vFilter: EditText by bindView(R.id.vFilter)
    private val vFruits: RecyclerView by bindView(R.id.vFruits)

    private val disposables = CompositeDisposable()

    override fun attach() {
        vFruits.layoutManager = LinearLayoutManager(activity)

        disposables.add(
            RxTextView.afterTextChangeEvents(vFilter)
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map { t: TextViewAfterTextChangeEvent -> t.editable().toString() }
                .filter({ it.isEmpty() || it.length > 2 })
                .subscribe({ t: String -> fruitsPresenter.filterFruits(t) }))
    }

    override fun displayFruits(fruits : List<Fruit>) {
        vLoading.visibility = View.GONE
        vEmpty.visibility = View.GONE
        vFilter.visibility = View.VISIBLE

        vFruits.apply {
            visibility = View.VISIBLE
            adapter = FruitsAdapter(fruits).apply {
                setItemClickListener(object: FruitsAdapter.OnClickListener {

                    override fun onClick(fruit: Fruit) {
                        router.pushController(
                            RouterTransaction
                                    .with(FruitController(fruit.id))
                                    .pushChangeHandler(FadeChangeHandler())
                                    .popChangeHandler(FadeChangeHandler()))
                    }

                })
            }
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
        Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
    }

    override fun detach() {
        disposables.dispose()

        vFruits.adapter = null
    }

}