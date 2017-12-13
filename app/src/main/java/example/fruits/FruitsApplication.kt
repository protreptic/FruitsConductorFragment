package example.fruits

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.setSingletonInstance
import example.fruits.data.remote.fruit.FruitsApiProvider.initApi
import io.realm.Realm
import io.realm.RealmConfiguration

class FruitsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initRealm()
        initPicasso()
        initApplication()
    }

    private fun initApplication() {
        initApi(this)
    }

    private fun initRealm() {
        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        try {
            Realm.setDefaultConfiguration(configuration)
            Realm.getDefaultInstance().close()
        } catch (e: Exception) {
            /*
             * Если что-то пошло не так, то сбрасываем хранилище.
             *
             * Что может пойти не так:
             * -----------------------
             * Конфликтное изменение схемы данных,
             * требующее миграции.
             */
            Realm.deleteRealm(configuration)
        }
    }

    private fun initPicasso() {
        setSingletonInstance(
                Picasso.Builder(this)
                        .downloader(OkHttp3Downloader(this))
                        .loggingEnabled(false)
                        .indicatorsEnabled(BuildConfig.DEBUG)
                        .build())
    }

}