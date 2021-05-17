package com.example.movie_detail.Views.MovieDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.movie_detail.Models.TMDBViewModel
import com.example.movie_detail.R
import com.example.movie_detail.Utils.Feedback
import com.example.movie_detail.Utils.NumberFormatters
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
        viewBinding = MovieDetailsBinding.inflate(inflater, container, false);
        configure();
        bindObservers();
        bindViews();
        return viewBinding.root;
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        try {
            requestData();
            Feedback.displaySnackBar(viewBinding.root, "Dados carregados com sucesso!");
        } catch (e: Exception) {
            // Display message and try to show the movie detail data
            val movieOverview = theMovieDatabaseViewModel.getMovieDetails().value;
            val availableData = movieOverview?.movieDetails != null;
            if (availableData) {
                viewBinding.noAvailableDataContainer.visibility = GONE;
                viewBinding.movieDetailsRootContainer.visibility = VISIBLE;
            } else {
                viewBinding.noAvailableDataContainer.visibility = VISIBLE;
                viewBinding.movieDetailsRootContainer.visibility = GONE;
            }
            Feedback.displaySnackBar(viewBinding.root, e.message.toString());
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun configure() {
        theMovieDatabaseViewModel.configure(getString(R.string.THE_MOVIE_DB_BASE_URL), getString(R.string.THE_MOVIE_DB_API_KEY));
    }

    fun bindObservers() {
        theMovieDatabaseViewModel.getMovieDetails().observe(viewLifecycleOwner, Observer {
            val similarMovies = it?.similarMovies ?: listOf();
            val votesCount: Long = it?.movieDetails?.voteCount?.toLong() ?: 0L;
            val popularity: Long = it?.movieDetails?.popularity?.toLong() ?: 0L;
            viewBinding.movieTitle = it?.movieDetails?.title;
            viewBinding.votesCount = NumberFormatters.getFormatedNumber(votesCount);
            viewBinding.moviePopularity = NumberFormatters.getFormatedNumber(popularity);
            viewBinding.movieImageEndpoint = it?.movieDetails?.imageUrl;
            viewBinding.similarMoviesList.adapter = SimilarMoviesAdapter(similarMovies, object: ISimilarMoviesAdapterCallbacks {
                override fun onClickMovie(position: Int) {
                    theMovieDatabaseViewModel.updateWatchedStatus(position);
                    viewBinding.similarMoviesList.adapter?.notifyItemChanged(position)
                }
            });
        });

        theMovieDatabaseViewModel.getResourceServerConfig().observe(viewLifecycleOwner, Observer {
            theMovieDbResourcesUrl = it?.images?.baseUrl ?: getString(R.string.THE_MOVIE_DB_DEFAULT_RESOURCES_BASE_URL);
        });

        theMovieDatabaseViewModel.getFavoriteStatus().observe(viewLifecycleOwner, Observer {
            viewBinding.isFavorite = it;
        })

        theMovieDatabaseViewModel.getLoadingStatus().observe(viewLifecycleOwner, Observer {
            if (it) {
                viewBinding.shimmerViewContainer.startShimmerAnimation();
            } else {
                viewBinding.shimmerViewContainer.stopShimmerAnimation();
            }
            viewBinding.isLoading = it;
        })
    }

    fun bindViews() {
        viewBinding.heartIcon.setOnClickListener {
            theMovieDatabaseViewModel.updateFavoriteStatus();
        }
    }

    fun requestData() {
        theMovieDatabaseViewModel.loadResourcesServerConfig();
        theMovieDatabaseViewModel.loadDataOfMovieId("509");
    }
}