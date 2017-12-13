package example.fruits.presentation.fragment.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AbsFragment : Fragment() {

    @LayoutRes
    abstract fun targetLayout() : Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(targetLayout(), container, false)
    }

}