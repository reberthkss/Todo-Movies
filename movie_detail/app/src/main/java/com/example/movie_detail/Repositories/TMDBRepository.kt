package com.example.movie_detail.Repositories

import android.provider.Settings.Global.getString
import android.util.Log
import com.example.movie_detail.Dataclasses.GetSimilarMoviesResponse
import com.example.movie_detail.Network.TMDBapi
import com.example.movie_detail.R
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class TMDBRepository(baseUrl: String, private val apiKey: String) {
    private val moshi = Moshi.Builder().build();
    private val api by lazy {
        Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
            .create(TMDBapi::class.java)
    }

    suspend fun getMovieDetail(movieId: String) = withContext(Dispatchers.IO) {
        val response = api.loadMovieId(movieId, apiKey).await();
        Log.d("MovieDetailsRepository", "data => ${response}");
    }

    suspend fun getIdOfSimilarMovies(movieId: String) = withContext(Dispatchers.IO) {
        val request = api.loadSimilarMovies(movieId, apiKey);
        request.enqueue(object: Callback<GetSimilarMoviesResponse> {
            override fun onFailure(call: Call<GetSimilarMoviesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<GetSimilarMoviesResponse>,
                response: Response<GetSimilarMoviesResponse>
            ) {
                TODO("Not yet implemented")
            }
        })
    }
}