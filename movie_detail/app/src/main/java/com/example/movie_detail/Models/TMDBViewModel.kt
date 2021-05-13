package com.example.movie_detail.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_detail.Dataclasses.SimpleMovieData
import com.example.movie_detail.Repositories.TMDBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TMDBViewModel() : ViewModel() {
    private val movieSimpleData: MutableLiveData<SimpleMovieData> = MutableLiveData()
    private lateinit var theMovieDatabaseRepository: TMDBRepository
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false);

    fun configure(baseUrl: String, apiKey: String) {
        theMovieDatabaseRepository = TMDBRepository(baseUrl, apiKey);
    }

    public fun loadDataOfMovieId(movieId: String): Unit {
        // TODO - Error handling
        viewModelScope.launch {
            if (this@TMDBViewModel::theMovieDatabaseRepository.isInitialized) {
                isLoading.value = true
                // Load data
                val movieDetails = theMovieDatabaseRepository.getMovieDetail(movieId);
                val movieIds = theMovieDatabaseRepository.getIdOfSimilarMovies(movieId);
                val similarMovies = theMovieDatabaseRepository.getMovieDetailsFromList(movieIds.results);
                // Save data
                movieSimpleData.value?.movieDetails = movieDetails;
                movieSimpleData.value?.similarMovies = similarMovies;
                isLoading.value = false
            }
        }
    }
    fun getMovieDetails(): LiveData<SimpleMovieData>{return movieSimpleData} // TODO - Implement
}