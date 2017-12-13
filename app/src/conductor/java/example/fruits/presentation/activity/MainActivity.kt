package example.fruits.presentation.activity

import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import example.fruits.presentation.activity.base.AbsActivity
import example.fruits.presentation.conductor.fruit.FruitsController

class MainActivity: AbsActivity() {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Conductor.attachRouter(this, container, savedInstanceState)

        if (!router!!.hasRootController()) {
            router!!.setRoot(RouterTransaction.with(FruitsController())
                    .pushChangeHandler(FadeChangeHandler()))
        }
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }

}