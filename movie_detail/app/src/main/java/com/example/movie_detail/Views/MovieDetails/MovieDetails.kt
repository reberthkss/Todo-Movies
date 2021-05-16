package com.example.movie_detail.Views.MovieDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.movie_detail.Models.TMDBViewModel
import com.example.movie_detail.R
import com.example.movie_detail.Views.MovieDetails.Lists.ISimilarMoviesAdapterCallbacks
import com.example.movie_detail.Views.MovieDetails.Lists.SimilarMoviesAdapter
import com.example.movie_detail.databinding.MovieDetailsBinding

class MovieDetails : Fragment() {
    lateinit var viewBinding: MovieDetailsBinding
    private val theMovieDatabaseViewModel: TMDBViewModel by activityViewModels();
    private var theMovieDbResourcesUrl: String? = null;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MovieDetails", "Hello world from movie details!");
        viewBinding = MovieDetailsBinding.inflate(inflater, container, false);
        theMovieDatabaseViewModel.configure(getString(R.string.THE_MOVIE_DB_BASE_URL), getString(R.string.THE_MOVIE_DB_API_KEY));
        theMovieDatabaseViewModel.getMovieDetails().observe(viewLifecycleOwner, Observer {
            val similarMovies = it?.similarMovies ?: listOf();
            viewBinding.votesCount = it?.movieDetails?.voteCount.toString() ?: "0";
            viewBinding.moviePopularity = it?.movieDetails?.popularity ?: 0f;
            viewBinding.movieImageEndpoint = it?.movieDetails?.imageUrl;
            viewBinding.similarMoviesList.adapter = SimilarMoviesAdapter(similarMovies, object: ISimilarMoviesAdapterCallbacks {
                override fun onClickMovie(position: Int) {
                    Log.d("Fragment", " Updating favorite value...");
                    theMovieDatabaseViewModel.updateWatchedStatus(position);
                    viewBinding.similarMoviesList.adapter?.notifyItemChanged(position)
                }
            });
        });

        theMovieDatabaseViewModel.getLoadingStatus().observe(viewLifecycleOwner, Observer {
            // TODO - Implement
        });

        theMovieDatabaseViewModel.getResourceServerConfig().observe(viewLifecycleOwner, Observer {
            theMovieDbResourcesUrl = it?.images?.baseUrl ?: getString(R.string.THE_MOVIE_DB_DEFAULT_RESOURCES_BASE_URL);
            Log.d("Fragment", "base url => ${theMovieDbResourcesUrl}")
        });

        theMovieDatabaseViewModel.getFavoriteStatus().observe(viewLifecycleOwner, Observer {
            viewBinding.isFavorite = it;
        })

        viewBinding.heartIcon.setOnClickListener {
            theMovieDatabaseViewModel.updateFavoriteStatus();
        }
        return viewBinding.root;
    }

    override fun onStart() {
        super.onStart()
        theMovieDatabaseViewModel.loadResourcesServerConfig();
        theMovieDatabaseViewModel.loadDataOfMovieId("550");
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}