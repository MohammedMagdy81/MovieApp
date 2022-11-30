package ddd.magdy.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import ddd.magdy.movieapp.R
import ddd.magdy.movieapp.adapters.LoadMoreAdapter
import ddd.magdy.movieapp.adapters.MovieAdapter
import ddd.magdy.movieapp.databinding.FragmentMovieBinding
import ddd.magdy.movieapp.repository.MovieRepository
import ddd.magdy.movieapp.response.MovieResponse
import ddd.magdy.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.collect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    private val viewModel: MovieViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.movieList.collect {
                    movieAdapter.submitData(it)
                }
            }
            movieRv.adapter = movieAdapter

            lifecycleScope.launchWhenCreated {
                movieAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    progressBar.isVisible = state is LoadState.Loading
                }
            }

            movieRv.adapter = movieAdapter.withLoadStateFooter(
                LoadMoreAdapter {
                    movieAdapter.retry()
                }
            )

            movieAdapter.onItemMovieClickLister {
                val action =
                    MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment(it.id!!)
                findNavController().navigate(action)
            }
        }
    }

}














