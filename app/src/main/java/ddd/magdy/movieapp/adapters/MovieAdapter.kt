package ddd.magdy.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ddd.magdy.movieapp.databinding.ItemLayoutBinding
import ddd.magdy.movieapp.response.ResultsItem
import ddd.magdy.movieapp.utils.Constants
import javax.inject.Inject


class MovieAdapter @Inject constructor() :
    PagingDataAdapter<ResultsItem, MovieAdapter.MovieViewHolder>(diffUtil) {


    inner class MovieViewHolder(val itemBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ResultsItem?) {
            itemBinding.apply {
                movieName.text = item?.originalTitle
                movieStar.text = item?.voteAverage.toString()
                language.text = item?.originalLanguage
                date.text = item?.releaseDate
                val imageUrl = Constants.POPULAR_BASEURL + item?.posterPath
                Glide.with(itemBinding.root).load(imageUrl).into(movieImage)
                root.setOnClickListener {
                    onMovieItemClick?.let { it ->
                        it(item!!)
                    }
                }

            }
        }

    }

    private var onMovieItemClick: ((ResultsItem) -> Unit)? = null
    fun onItemMovieClickLister(listener: ((ResultsItem) -> Unit)) {
        onMovieItemClick = listener
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding: ItemLayoutBinding =
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}