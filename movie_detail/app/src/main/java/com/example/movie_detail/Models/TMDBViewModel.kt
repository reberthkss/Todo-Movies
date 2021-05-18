package com.example.movie_detail.Models

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_detail.Dataclasses.SimpleMovieData
import com.example.movie_detail.Dataclasses.TMDBResourceConfig
import com.example.movie_detail.Repositories.TMDBRepository
import com.example.movie_detail.Room.Entities.Movie.MovieEntity
import com.example.movie_detail.Room.Relations.MovieWithGenresAndSimilarMovies
import com.example.movie_detail.Utils.Feedback
import com.example.movie_detail.Views.MovieDetails.MovieDetails
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class TMDBViewModel() : ViewModel() {
    private val TAG = " TMDBViewModel";
    private val resourcesServerConfiguration: MutableLiveData<TMDBResourceConfig?> = MutableLiveData(null);
    private val movieIsFavorite: MutableLiveData<Boolean> = MutableLiveData(false);
    private lateinit var theMovieDatabaseRepository: TMDBRepository
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false);
    private var movieDetails: LiveData<MovieWithGenresAndSimilarMovies?>? = null ;
    fun configure(baseUrl: String, apiKey: String, ctx: Context, movieId: String) {
        theMovieDatabaseRepository = TMDBRepository(baseUrl, apiKey, ctx, movieId);
        movieDetails = theMovieDatabaseRepository.movieDetails as LiveData<MovieWithGenresAndSimilarMovies?>?;
    }

    fun loadDataOfMovieId() {
        try {
            if (this::theMovieDatabaseRepository.isInitialized) {
                viewModelScope.launch {
                    theMovieDatabaseRepository.getMovieDetailsById();
                    theMovieDatabaseRepository.getSimilarlyMoviesOfId();
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun loadGenres() {
        try {
            if (this::theMovieDatabaseRepository.isInitialized) {
                viewModelScope.launch {
                    theMovieDatabaseRepository.getAllGenres();
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun loadResourcesServerConfig() {
        try {
            viewModelScope.launch {
                if (this@TMDBViewModel::theMovieDatabaseRepository.isInitialized) {
                    val resourceServerConfig = theMovieDatabaseRepository.getResourcesConfiguration();
                    resourcesServerConfiguration.value = resourceServerConfig;
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun loadMovieData() {
        try {
            if (this::theMovieDatabaseRepository.isInitialized) {
                isLoading.value = true;
                loadResourcesServerConfig();
                loadDataOfMovieId();
                loadGenres();
                isLoading.value = false;
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString());
        }
    }

    fun getMovieDetails(): LiveData<MovieWithGenresAndSimilarMovies?>? {
        return movieDetails;
    }
    fun updateWatchedStatus(position: Int) {
        try {

        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun updateFavoriteStatus() {
        try {

        } catch (e: Exception) {
            Log.d(TAG, e.message.toString());
        }
    }

    fun getLoadingStatus(): LiveData<Boolean> {return isLoading};
    fun getResourceServerConfig(): LiveData<TMDBResourceConfig?> {return resourcesServerConfiguration};
    fun getFavoriteStatus(): LiveData<Boolean> {return movieIsFavorite};
}