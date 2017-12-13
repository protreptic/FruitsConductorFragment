package example.fruits.presentation.fruit

import example.fruits.data.model.Fruit
import example.fruits.data.remote.fruit.FruitsApi
import example.fruits.data.repository.FruitsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import example.fruits.presentation.fruit.FruitsPresenter.FruitsView

class FruitsPresenterImpl(
        private val view: FruitsView,
        private val repository: FruitsRepository,
        private val fruitsApi: FruitsApi): FruitsPresenter {

    private val disposables = CompositeDisposable()

    override fun attach() {
        view.attach()
    }

    override fun displayFruits() {
        disposables.add(
            repository
                .fetchRx()
                .subscribe({ fruits ->
                    checkIfEmptyOrDisplay(fruits)
                }, {
                    view.displayError()
                }))

        updateFruits()
    }

    override fun filterFruits(name: String) {
        disposables.add(
            repository
                .fetchByNameRx(name)
                .subscribe { fruits ->
                    checkIfEmptyOrDisplay(fruits)
                })
    }

    private fun checkIfEmptyOrDisplay(fruits: List<Fruit>) {
        if (fruits.isEmpty()) {
            view.displayEmpty()
        } else {
            view.displayFruits(fruits)
        }
    }

    private fun updateFruits() {
        disposables.add(
            fruitsApi
                .fruits()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ fruits ->
                    repository.retain(fruits)

                    checkIfEmptyOrDisplay(fruits)
                }, {
                    view.displayError()
                }))
    }

    override fun detach() {
        disposables.dispose()

        view.detach()
    }

}