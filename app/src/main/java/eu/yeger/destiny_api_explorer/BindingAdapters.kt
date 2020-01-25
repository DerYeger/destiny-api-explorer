package eu.yeger.destiny_api_explorer

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import eu.yeger.destiny_api_explorer.domain.ItemDefinition
import eu.yeger.destiny_api_explorer.network.BUNGIE_BASE_URL
import eu.yeger.destiny_api_explorer.ui.ItemGridAdapter

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }
}

@BindingAdapter("bungieImageUrl")
fun ImageView.bindBungieImage(imageUrl: String?) =
    bindImage(BUNGIE_BASE_URL + imageUrl)

@BindingAdapter("itemList")
fun RecyclerView.bindRecyclerView(data: List<ItemDefinition>?) {
    val adapter = adapter as ItemGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.bindSwipeRefreshLayout(listener: Runnable) {
    setOnRefreshListener {
        listener.run()
    }
}
