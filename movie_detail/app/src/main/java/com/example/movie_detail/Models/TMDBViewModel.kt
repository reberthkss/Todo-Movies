package com.example.movie_detail.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val supervisorJob = SupervisorJob();
    private val coroutineScope = CoroutineScope(Dispatchers.Main + supervisorJob);

    fun configure(baseUrl: String, apiKey: String) {
        theMovieDatabaseRepository = TMDBRepository(baseUrl, apiKey);
    }

    public fun loadDataOfMovieId(movieId: String): Unit {
        isLoading.value = true
        // TODO - Error handling
        coroutineScope.launch {
            if (this@TMDBViewModel::theMovieDatabaseRepository.isInitialized) {
                // Load data
                val movieDetails = theMovieDatabaseRepository.getMovieDetail(movieId);
                val movieIds = theMovieDatabaseRepository.getIdOfSimilarMovies(movieId);
                val similarMovies = theMovieDatabaseRepository.getMovieDetailsFromList(movieIds.results);
                // Save data
                movieSimpleData.value?.movieDetails = movieDetails;
                movieSimpleData.value?.similarMovies = similarMovies;
            }
        }
        isLoading.value = false
    }
    fun getMovieDetails(): LiveData<SimpleMovieData>{return movieSimpleData} // TODO - Implement
}