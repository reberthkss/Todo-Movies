package com.example.movie_detail.Annotations

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter(value = ["imageUrl", "imageSize", "baseUrl"], requireAll = false)
fun setImageUrl(imageView: ImageView, imageUrl: String?, imageSize: String?, resourcesBaseUrl: String?) {
    val baseUrl = resourcesBaseUrl;
    if (imageUrl != null) {
        val imagePath = baseUrl + imageSize + imageUrl;
        Log.d("BindingAdapter", "url => ${imagePath} ")
        val URI = imagePath.toUri().buildUpon().scheme("https").build();
        Glide
            .with(imageView.context)
            .load(URI)
            .into(imageView)
    }
}