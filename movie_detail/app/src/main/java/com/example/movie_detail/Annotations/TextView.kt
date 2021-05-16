package com.example.movie_detail.Annotations

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.movie_detail.R

@BindingAdapter("app:popularity")
fun setPopularity(textView: TextView, popularity: Float?) {
    if (popularity != null) {
        textView.text = ""
    }
}