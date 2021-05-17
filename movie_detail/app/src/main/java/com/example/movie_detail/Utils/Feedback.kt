package com.example.movie_detail.Utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Feedback {
    companion object {
        fun displaySnackBar(view: View, message: String) {
            Snackbar
                .make(view, message, Snackbar.LENGTH_SHORT)
                .show();
        }
    }
}