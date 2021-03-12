package com.example.samplemovie.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.samplemovie.NetworkConfig
import com.example.samplemovie.R
import com.example.samplemovie.adapter.ReviewerAdapter
import com.example.samplemovie.data.local.FavouriteDatabase
import com.example.samplemovie.data.model.ResultsData
import com.example.samplemovie.data.model.ReviewData
import com.example.samplemovie.data.model.ReviewerData
import com.example.samplemovie.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        getReviewMovie()
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

    private fun getReviewMovie(){
        val movieId = movieDetail?.id
        val call: Call<ReviewerData> = NetworkConfig.getClient().getMovieReviews(movieId)
        call.enqueue(object : Callback<ReviewerData> {

            override fun onResponse(
                call: Call<ReviewerData>?,
                response: Response<ReviewerData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.results ?: emptyList()
                    if ((data as ArrayList<ReviewData>).isEmpty()){
                        binding.tvNoReview.visibility = View.VISIBLE
                    } else {
                        binding.tvNoReview.visibility = View.GONE
                    }
                    binding.rvReviewMoviewList.apply {
                        val listReviewer = ReviewerAdapter(data as ArrayList<ReviewData>)
                        adapter = listReviewer
                        layoutManager = LinearLayoutManager(applicationContext)
                        setHasFixedSize(true)
                    }

                } else {
                    Log.e( "error: ", "error")
                }
            }

            override fun onFailure(call: Call<ReviewerData>?, t: Throwable?) {
                Log.e( "call: ", call.toString())
                Log.e( "Throwable: ", t.toString())
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val MOVIE = "movie"
    }
}