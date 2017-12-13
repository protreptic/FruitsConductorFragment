package fruits.presentation.activity

import android.os.Bundle
import fruits.presentation.activity.base.AbsActivity
import fruits.presentation.fragment.fruit.FruitsFragment

class MainActivity : AbsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(android.R.id.content,
                            FruitsFragment.newInstance(), FruitsFragment.TAG)
                    .commit()
        }
    }

}