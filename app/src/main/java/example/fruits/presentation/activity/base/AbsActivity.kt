package example.fruits.presentation.activity.base

import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup

abstract class AbsActivity : AppCompatActivity() {

    protected val container: ViewGroup by lazy {
        findViewById<ViewGroup>(android.R.id.content)
    }

}