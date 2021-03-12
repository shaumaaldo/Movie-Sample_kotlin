package com.example.samplemovie.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.samplemovie.R
import com.example.samplemovie.data.local.FavouriteDatabase
import com.example.samplemovie.data.model.ResultsData
import com.example.samplemovie.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isFav: Boolean = false
    private val database = FavouriteDatabase(this)
    private var movieDetail: ResultsData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initListener()
    }

    private fun initView(){
        movieDetail = intent.getParcelableExtra(MOVIE)

        title = movieDetail?.title
        binding.tvTitleMovie.text = title
        binding.tvReleaseDate.text = movieDetail?.release_date
        binding.tvOverview.text = getString(R.string.overview)
        binding.tvDescMovie.text = movieDetail?.overview

        val localMovie = database.getFavouriteMovieById(movieDetail?.id)

        if (localMovie != null){
            binding.ibFav.setBackgroundResource(R.drawable.ic_fav_full)
            binding.ibFav.background.setTint(resources.getColor(R.color.red, null))
        } else {
            binding.ibFav.setBackgroundResource(R.drawable.ic_fav)
            binding.ibFav.background.setTint(resources.getColor(R.color.grey, null))
        }

        Glide.with(binding.ivImgMovie)
            .load("https://image.tmdb.org/t/p/w500/${movieDetail?.poster_path}")
            .fitCenter()
            .into(binding.ivImgMovie)

        setSupportActionBar(binding.tbDetail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initListener(){
        binding.ibFav.setOnClickListener{
            isFav = if (!isFav){
                binding.ibFav.setBackgroundResource(R.drawable.ic_fav_full)
                binding.ibFav.background.setTint(resources.getColor(R.color.red, null))
                database.addFavouriteMovie(movieDetail)
                Toast.makeText(this, "${movieDetail?.title} ditambahkan ke favourite !", Toast.LENGTH_SHORT).show()
                true
            } else {
                binding.ibFav.setBackgroundResource(R.drawable.ic_fav)
                binding.ibFav.background.setTint(resources.getColor(R.color.grey, null))
                database.deleteFavouriteMovie(movieDetail)
                Toast.makeText(this, "${movieDetail?.title} dihapus dari favourite !", Toast.LENGTH_SHORT).show()
                false
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val MOVIE = "movie"
    }
}