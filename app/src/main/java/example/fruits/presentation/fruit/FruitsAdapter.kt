package example.fruits.presentation.fruit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import example.fruits.data.model.Fruit
import example.fruits.R
import kotterknife.bindView

class FruitsAdapter(private val fruits : List<Fruit>) : RecyclerView.Adapter<FruitsAdapter.ViewHolder>() {

    interface OnClickListener {

        /**
         *
         */
        fun onClick(fruit: Fruit)

    }

    override fun getItemCount(): Int = fruits.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context)
                        .inflate(R.layout.fruit_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val fruit = fruits[position]

        holder?.apply {
            bindFruitPicture(vPicture, fruit)

            vName.text = fruit.name

            itemView.setOnClickListener {
                itemClickListener?.onClick(fruit)
            }
        }
    }

    private var itemClickListener: OnClickListener? = null

    fun setItemClickListener(listener: OnClickListener) {
        itemClickListener = listener
    }

    private fun bindFruitPicture(vPicture : ImageView, fruit: Fruit) {
        val ctx = vPicture.context

        Picasso.with(ctx)
                .load("${ctx.getString(R.string.api_fruits)}fruit/${fruit.id}/picture")
                .resize(64, 64)
                .centerCrop()
                .into(vPicture)
    }

    inner class ViewHolder(view : View?) : RecyclerView.ViewHolder(view) {

        val vPicture : ImageView by bindView(R.id.vPicture)
        val vName : TextView by bindView(R.id.vName)

    }

}