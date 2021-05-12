package com.example.movie_detail.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieDetails : ViewModel() {
    private lateinit var data: MutableLiveData<Any>; // TODO - Create data class
    private var isLoading: Boolean = false;

    fun loadData(): Unit {} // TODO - Implement
    fun getData(): LiveData<Any>{return data} // TODO - Implement
}