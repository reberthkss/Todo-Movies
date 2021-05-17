package com.example.movie_detail.Annotations

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter
import com.facebook.shimmer.ShimmerFrameLayout

@BindingAdapter("app:visibility")
fun setVisibility(shimmerLayout: ShimmerFrameLayout, visibility: String) {
    shimmerLayout.visibility  = if (visibility == "visible") VISIBLE else GONE;
}