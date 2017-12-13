package example.fruits.presentation.conductor.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import example.fruits.util.ViewBinder

abstract class AbsController: Controller {

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    @LayoutRes
    abstract fun targetLayout() : Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(targetLayout(), container, false)
    }

    @CallSuper
    override fun onAttach(view: View) {
        ViewBinder.setup(this, view)
    }

    @CallSuper
    override fun onDetach(view: View) {
        ViewBinder.tearDown(this)
    }

}