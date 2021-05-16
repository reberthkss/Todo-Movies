package com.example.movie_detail.Views.MovieDetails.Lists

import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_detail.Dataclasses.MovieDetailsDataclasse
import com.example.movie_detail.databinding.MovieDetailsRecViewItemAdapterBinding

class SimilarMoviesAdapter(val similarMovies: List<MovieDetailsDataclasse>, val callbacks: ISimilarMoviesAdapterCallbacks): RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MovieDetailsRecViewItemAdapterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movieDetail: MovieDetailsDataclasse, position: Int) {
            Log.d("Adapter", "movie detail => ${movieDetail}")
            binding.movieTitle = movieDetail.title;
//            binding.movieDescription = movi
            binding.movieImageUrl = movieDetail.imageUrl;
            binding.isFavorite = movieDetail.alreadyWatched;

            if (movieDetail.alreadyWatched) {
                binding.checkedIcon.visibility = VISIBLE;
            } else {
                binding.checkedIcon.visibility = GONE;
            }
            binding.movieCardContainer.setOnClickListener {
                callbacks.onClickMovie(position);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val binding = MovieDetailsRecViewItemAdapterBinding.inflate(inflater, parent, false);
        return ViewHolder(binding);
    }

    override fun getItemCount(): Int {
        return similarMovies.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(similarMovies[position], position)
    }
}