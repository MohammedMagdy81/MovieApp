package ddd.magdy.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ddd.magdy.movieapp.paging.MoviePagingSource
import ddd.magdy.movieapp.repository.MovieRepository
import ddd.magdy.movieapp.response.MovieDetailsResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val movieList = Pager(PagingConfig(1)) {
        MoviePagingSource(repository)
    }.flow.cachedIn(viewModelScope)


    val loading = MutableLiveData<Boolean>(true)

    val detailsMovieList = MutableLiveData<MovieDetailsResponse>()
    fun getMovieDetails(id: Int) = viewModelScope.launch {
        val response = repository.getMovieDetailById(id)
        if (response.isSuccessful) {
            detailsMovieList.postValue(response.body())
        }
        loading.postValue(false)
    }
}