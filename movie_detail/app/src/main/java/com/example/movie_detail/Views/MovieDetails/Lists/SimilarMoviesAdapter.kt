package com.example.movie_detail.Views.MovieDetails.Lists

import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_detail.Dataclasses.MovieDetailsDataclasse
import com.example.movie_detail.databinding.MovieDetailsRecViewItemAdapterBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class SimilarMoviesAdapter(val similarMovies: List<MovieDetailsDataclasse>, val callbacks: ISimilarMoviesAdapterCallbacks): RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MovieDetailsRecViewItemAdapterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movieDetail: MovieDetailsDataclasse, position: Int) {
            val releaseDateFormatter = DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss][.SSS]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter()
            val releaseYearFormatter = DateTimeFormatter.ofPattern("yyyy");
            val releaseDate = if (movieDetail.releaseDate.isNotEmpty()) LocalDate.parse(movieDetail.releaseDate, releaseDateFormatter) else LocalDate.now();
            binding.movieTitle = movieDetail.title;
            binding.movieDescription = releaseDate.format(releaseYearFormatter) + " " + movieDetail.genres.map {it.name}.reduce {acc, genre -> "${acc}, ${genre}"};
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