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
        } catch (e: Exception) {
            Log.d("Fragment", "Error on request data => ${e.message}");
        }
    }

    override fun onDestroyView() {
        super.onDestroyView();
    }

    fun configure() {
        theMovieDatabaseViewModel.configure(getString(R.string.THE_MOVIE_DB_BASE_URL), getString(R.string.THE_MOVIE_DB_API_KEY), requireContext(), "509")
    }


    fun observeMovieDetails() {
        theMovieDatabaseViewModel.getMovieDetail().removeObservers(viewLifecycleOwner);
        theMovieDatabaseViewModel.getMovieDetail().observe(viewLifecycleOwner, Observer {
            Log.d("Fragment", "movie => ${it}")
            if (it != null) {
                viewBinding.movieTitle = it.movie.movieTitle
                viewBinding.votesCount = NumberFormatters.getFormatedNumber(it.movie.voteCount);
                viewBinding.moviePopularity = NumberFormatters.getFormatedNumber(it.movie.popularity.toLong());
                viewBinding.movieImageEndpoint = it.movie.movieImageUrl;
                viewBinding.isFavorite = it.movie.isFavorite;
            }
        });
    }

    fun observeSimilarMovies() {
        theMovieDatabaseViewModel.getSimilarMovies().removeObservers(viewLifecycleOwner);
        theMovieDatabaseViewModel.getSimilarMovies().observe(viewLifecycleOwner, Observer {
            Log.d("fragment", "list => ${it}")
            if (it != null && it.similarMovies.isNotEmpty()) {
                viewBinding.similarMoviesList.adapter = SimilarMoviesAdapter(it.similarMovies, object: ISimilarMoviesAdapterCallbacks {
                    override fun onClickMovie(position: Int) {
                        theMovieDatabaseViewModel.updateWatchedStatus(position);
                        viewBinding.similarMoviesList.adapter?.notifyItemChanged(position);
                    }
                })
            }
        })
    }
    fun bindObservers() {

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
                observeMovieDetails();
                observeSimilarMovies();
                Feedback.displaySnackBar(viewBinding.root, "Dados carregados com sucesso!");
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
        theMovieDatabaseViewModel.loadMovieData();
    }
}