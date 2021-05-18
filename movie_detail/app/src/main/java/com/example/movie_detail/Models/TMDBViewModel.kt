package com.example.movie_detail.Models

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_detail.Dataclasses.TMDBResourceConfig
import com.example.movie_detail.Repositories.TMDBRepository
import com.example.movie_detail.Room.Relations.MovieWithGenres
import com.example.movie_detail.Room.Relations.MovieWithSimilarMovies
import com.example.movie_detail.Utils.Network
import kotlinx.coroutines.launch
import java.lang.Exception

class TMDBViewModel() : ViewModel() {
    private val TAG = " TMDBViewModel";
    private val resourcesServerConfiguration: MutableLiveData<TMDBResourceConfig?> = MutableLiveData(null);
    private val movieIsFavorite: MutableLiveData<Boolean> = MutableLiveData(false);
    private lateinit var theMovieDatabaseRepository: TMDBRepository
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false);
    private val movieWithGenres: MutableLiveData<MovieWithGenres> = MutableLiveData() ;
    private val similarMovies: MutableLiveData<MovieWithSimilarMovies> = MutableLiveData();
    fun configure(baseUrl: String, apiKey: String, ctx: Context, movieId: String) {
        theMovieDatabaseRepository = TMDBRepository(baseUrl, apiKey, ctx, movieId);
    }

    suspend fun loadDataOfMovieId() {
        theMovieDatabaseRepository.getMovieDetailsById();
        theMovieDatabaseRepository.getSimilarlyMoviesOfId();
    }

    suspend fun loadGenres() {
        theMovieDatabaseRepository.getAllGenres();

    }

    suspend fun loadResourcesServerConfig() {
        val resourceServerConfig = theMovieDatabaseRepository.getResourcesConfiguration();
        resourcesServerConfiguration.value = resourceServerConfig;
    }

    suspend fun loadMovie() {
        movieWithGenres.value = theMovieDatabaseRepository.getMovieWithGenre();
    }

    suspend fun loadSimilarMovies() {
        similarMovies.value = theMovieDatabaseRepository.getSimilarMovieWithGenre();
    }

    fun loadMovieData() {
        try {
            if (this::theMovieDatabaseRepository.isInitialized) {
                viewModelScope.launch {
                    isLoading.value = true;
                    if (Network.hasInternetConnection()) {
                        Log.d("model", "has connection!!!")
                        loadResourcesServerConfig();
                        loadDataOfMovieId();
                        loadGenres();
                    }
                    loadMovie();
                    loadSimilarMovies();
                    isLoading.value = false;
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString());
        }
    }


    fun getMovieDetail(): LiveData<MovieWithGenres> {
        return movieWithGenres
    }

    fun getSimilarMovies(): LiveData<MovieWithSimilarMovies> {
        return similarMovies;
    }

    fun updateWatchedStatus(position: Int) {
        try {
            if (this::theMovieDatabaseRepository.isInitialized) {
                viewModelScope.launch {
                    similarMovies.value
                        ?.similarMovies?.get(position)
                        ?.apply {
                            this.similarMovie.isWatched = !this.similarMovie.isWatched;
                            theMovieDatabaseRepository.updateSimilarMovieEntity(this.similarMovie)
                        }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun updateFavoriteStatus() {
        try {
            if (this::theMovieDatabaseRepository.isInitialized) {
                viewModelScope.launch {
                    movieWithGenres.value?.movie?.apply {
                        isFavorite = !isFavorite;
                        theMovieDatabaseRepository.updateMovieEntity(this);
                        movieWithGenres.value = movieWithGenres.value;
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString());
        }
    }

    fun getLoadingStatus(): LiveData<Boolean> {return isLoading};
    fun getResourceServerConfig(): LiveData<TMDBResourceConfig?> {return resourcesServerConfiguration};
    fun getFavoriteStatus(): LiveData<Boolean> {return movieIsFavorite};
}