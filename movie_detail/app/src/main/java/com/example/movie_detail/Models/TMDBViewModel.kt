package com.example.movie_detail.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_detail.Repositories.TMDBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TMDBViewModel() : ViewModel() {
    private val data: MutableLiveData<Any> = MutableLiveData<Any>()// TODO - Create data class
    private lateinit var theMovieDatabaseRepository: TMDBRepository
    private var isLoading: Boolean = false;
    private val supervisorJob = SupervisorJob();
    private val coroutineScope = CoroutineScope(Dispatchers.Main + supervisorJob);

    fun configure(baseUrl: String, apiKey: String) {
        theMovieDatabaseRepository = TMDBRepository(baseUrl, apiKey);
    }

    fun loadMovieDetailsById(movieId: String): Unit {
//        TODO - Error handling
        coroutineScope.launch {
            if (this@TMDBViewModel::theMovieDatabaseRepository.isInitialized) {
                theMovieDatabaseRepository.getMovieDetail(movieId);
            }
        }
    } // TODO - Implement

    fun loadSimilarMoviesById(movieId: String): Unit {
        // TODO - Error handling
        coroutineScope.launch {
            if (this@TMDBViewModel::theMovieDatabaseRepository.isInitialized) {
                theMovieDatabaseRepository.getSimilarMovies(movieId);
            }
        }
    }
    fun getData(): LiveData<Any>{return data} // TODO - Implement
}