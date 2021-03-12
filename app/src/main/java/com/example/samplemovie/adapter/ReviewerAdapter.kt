package com.example.samplemovie.adapter

import com.example.samplemovie.data.model.ReviewData
import com.example.samplemovie.databinding.ItemReviewerListBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplemovie.R

class ReviewerAdapter internal constructor(private val listReviewer: ArrayList<ReviewData>) :
    RecyclerView.Adapter<ReviewerAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemCardBinding =
            ItemReviewerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(itemCardBinding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = listReviewer[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listReviewer.size
    }

    class CardViewHolder(private val reviewBinding: ItemReviewerListBinding) :
        RecyclerView.ViewHolder(reviewBinding.root) {
        fun bind(listReviewer: ReviewData) {
            Glide.with(reviewBinding.ivImgAuthor)
                .load("https://image.tmdb.org/t/p/w500/${listReviewer.avatar_path}")
                .placeholder(R.drawable.ic_launcher_foreground)
                .fitCenter()
                .into(reviewBinding.ivImgAuthor)

            reviewBinding.tvAuthor.text = listReviewer.author
            reviewBinding.tvContent.text = listReviewer.content
        }
    }
}