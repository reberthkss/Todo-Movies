package com.example.movie_detail.Views.MovieDetails.Lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_detail.Room.Relations.SimilarMovieWithGenre
import com.example.movie_detail.databinding.MovieDetailsRecViewItemAdapterBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class SimilarMoviesAdapter(val similarMovies: List<SimilarMovieWithGenre>, val callbacks: ISimilarMoviesAdapterCallbacks): RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MovieDetailsRecViewItemAdapterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movieDetail: SimilarMovieWithGenre, position: Int) {
            val releaseDateFormatter = DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss][.SSS]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter()
            val releaseYearFormatter = DateTimeFormatter.ofPattern("yyyy");
            val releaseDate = if (movieDetail.similarMovie.releaseDate.isNotEmpty()) LocalDate.parse(movieDetail.similarMovie.releaseDate, releaseDateFormatter) else LocalDate.now();
            binding.movieTitle = movieDetail.similarMovie.movieTitle;
            binding.movieDescription = releaseDate.format(releaseYearFormatter) + " " + movieDetail.genres.map {it.genreName}.reduce {acc, genre -> "${acc}, ${genre}"};
            binding.movieImageUrl = movieDetail.similarMovie.movieImageUrl;
            binding.isWatched = movieDetail.similarMovie.isWatched;

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