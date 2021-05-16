package com.example.movie_detail.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_detail.Dataclasses.SimpleMovieData
import com.example.movie_detail.Dataclasses.TMDBResourceConfig
import com.example.movie_detail.Repositories.TMDBRepository
import kotlinx.coroutines.launch

class TMDBViewModel() : ViewModel() {
    private val resourcesServerConfiguration: MutableLiveData<TMDBResourceConfig?> = MutableLiveData(null);
    private val movieSimpleData: MutableLiveData<SimpleMovieData?> = MutableLiveData(null);
    private val movieIsFavorite: MutableLiveData<Boolean> = MutableLiveData(false);
    private lateinit var theMovieDatabaseRepository: TMDBRepository
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false);

    fun configure(baseUrl: String, apiKey: String) {
        theMovieDatabaseRepository = TMDBRepository(baseUrl, apiKey);
    }

    fun loadDataOfMovieId(movieId: String): Unit {
        // TODO - Error handling
        viewModelScope.launch {
            if (this@TMDBViewModel::theMovieDatabaseRepository.isInitialized) {
                isLoading.value = true
                // Load data
                val movieDetails = theMovieDatabaseRepository.getMovieDetail(movieId);
                val movieIds = theMovieDatabaseRepository.getIdOfSimilarMovies(movieId);
                val similarMovies = theMovieDatabaseRepository.getMovieDetailsFromList(movieIds.results);
                // Save data
                movieSimpleData.value = SimpleMovieData(movieDetails, similarMovies)
                isLoading.value = false
            }
        }
    }

    fun loadResourcesServerConfig() {
        viewModelScope.launch {
            if (this@TMDBViewModel::theMovieDatabaseRepository.isInitialized) {
                val resourceServerConfig = theMovieDatabaseRepository.getResourcesConfiguration();
                resourcesServerConfiguration.value = resourceServerConfig;
            }
        }
    }

    fun updateWatchedStatus(position: Int) {
        if (movieSimpleData.value != null) {
            movieSimpleData.value!!.similarMovies[position].updateAlreadyWatchedValue();
        }
    }

    fun updateFavoriteStatus() {
        if (movieSimpleData.value != null) {
            movieSimpleData.value!!.movieDetails?.updateFavoriteValue();
            movieIsFavorite.value = movieSimpleData.value!!.movieDetails?.favorite ?: false;
        }
    }

    fun getMovieDetails(): LiveData<SimpleMovieData?> {return movieSimpleData};
    fun getLoadingStatus(): LiveData<Boolean> {return isLoading};
    fun getResourceServerConfig(): LiveData<TMDBResourceConfig?> {return resourcesServerConfiguration};
    fun getFavoriteStatus(): LiveData<Boolean> {return movieIsFavorite};
}