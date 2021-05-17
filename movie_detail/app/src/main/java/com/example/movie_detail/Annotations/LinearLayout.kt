package com.example.movie_detail.Annotations

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibility")
fun setVisibilityOfLinearLayout(layout: LinearLayout, newVisibility: String) {
    layout.visibility = if (newVisibility == "visible") VISIBLE else GONE;
}