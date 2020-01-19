package eu.yeger.destiny_api_explorer

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import eu.yeger.destiny_api_explorer.network.BUNGIE_BASE_URL

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("bungieImageUrl")
fun bindBungieImage(imageView: ImageView, imageUrl: String?) = bindImage(imageView, BUNGIE_BASE_URL + imageUrl)
