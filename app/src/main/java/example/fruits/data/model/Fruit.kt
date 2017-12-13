package example.fruits.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Fruit : RealmObject(), Nullable {

    companion object {

        const val F_ID = "id"
        const val F_NAME = "name"
    }

    @PrimaryKey
    var id: Int = 0
    var name: String = ""

    override fun isNull() =
            (id == 0 && name == "")

}