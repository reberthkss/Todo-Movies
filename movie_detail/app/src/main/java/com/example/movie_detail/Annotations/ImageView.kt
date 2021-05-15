package com.example.movie_detail.Annotations

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        val URI = imageUrl.toUri().buildUpon().scheme("https").build();
        Glide
            .with(imageView.context)
            .load(URI)
            .into(imageView)
    }
}