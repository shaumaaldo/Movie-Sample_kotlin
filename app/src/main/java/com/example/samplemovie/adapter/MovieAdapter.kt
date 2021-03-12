package com.example.samplemovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplemovie.data.model.ResultsData
import com.example.samplemovie.databinding.ItemMovieListBinding

class MovieAdapter internal constructor(private val listMovie: ArrayList<ResultsData>) :
RecyclerView.Adapter<MovieAdapter.CardViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemCardBinding =
            ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(itemCardBinding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = listMovie[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(item)}
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsData)
    }

    internal fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class CardViewHolder(private val movieBinding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(movieBinding.root) {
        fun bind(listMovie: ResultsData) {
            movieBinding.ivImgMovie
            Glide.with(movieBinding.ivImgMovie)
                .load("https://image.tmdb.org/t/p/w500/${listMovie.poster_path}")
                .fitCenter()
                .into(movieBinding.ivImgMovie)

            movieBinding.tvTitleMovie.text = listMovie.title
            movieBinding.tvReleaseDate.text = listMovie.release_date
        }
    }
}