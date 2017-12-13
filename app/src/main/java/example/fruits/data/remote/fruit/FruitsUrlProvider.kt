package example.fruits.data.remote.fruit

import android.content.Context
import example.fruits.R
import example.fruits.data.remote.UrlProvider

class FruitsUrlProvider(private val context: Context) : UrlProvider {

    override fun provide() : String {
        return context.getString(R.string.api_fruits)
    }

}