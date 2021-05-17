package com.example.movie_detail.Models

import android.content.Context
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

    fun configure(baseUrl: String, apiKey: String, ctx: Context) {
        theMovieDatabaseRepository = TMDBRepository(baseUrl, apiKey, ctx);
    }

    fun loadDataOfMovieId(movieId: String): Unit {
      
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

    }

    fun updateFavoriteStatus() {

    }

    fun getMovieDetails(): LiveData<SimpleMovieData?> {return movieSimpleData};
    fun getLoadingStatus(): LiveData<Boolean> {return isLoading};
    fun getResourceServerConfig(): LiveData<TMDBResourceConfig?> {return resourcesServerConfiguration};
    fun getFavoriteStatus(): LiveData<Boolean> {return movieIsFavorite};
}