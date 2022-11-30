package ddd.magdy.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ddd.magdy.movieapp.R
import ddd.magdy.movieapp.databinding.FragmentMovieBinding
import ddd.magdy.movieapp.databinding.FragmentMovieDetailsBinding
import ddd.magdy.movieapp.repository.MovieRepository
import ddd.magdy.movieapp.response.MovieDetailsResponse
import ddd.magdy.movieapp.utils.Constants
import ddd.magdy.movieapp.viewmodel.MovieViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.movieId
        viewModel.getMovieDetails(id)
        viewModel.detailsMovieList.observe(viewLifecycleOwner) { response ->
            val imageUrl = Constants.POPULAR_BASEURL + response.posterPath
            binding.apply {
                Glide.with(view).load(imageUrl).into(detailMovieImage)
                Glide.with(view).load(imageUrl).into(movieDetailImageBg)

                tvMovieTitle.text = response.originalTitle
                tvMovieDateRelease.text = response.releaseDate
                tvMovieBudget.text = response.budget.toString()
                tvMovieOverview.text = response.overview
                tvMovieRating.text = response.voteAverage.toString()
                tvMovieTagLine.text = response.tagline
                tvMovieRevenue.text = response.revenue.toString()
                tvMovieRuntime.text = response.runtime.toString()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { show ->
            if (show) {
                binding.progressBarDetails.visibility = View.VISIBLE
            } else {
                binding.progressBarDetails.visibility = View.GONE
            }
        }
    }

}












