package com.example.samplemovie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemovie.data.local.FavouriteDatabase
import com.example.samplemovie.data.model.ResultsData
import com.example.samplemovie.adapter.MovieAdapter
import com.example.samplemovie.databinding.ActivityFavBinding

class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavBinding
    private val database = FavouriteDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        title = "Favourite Movie List"

        setSupportActionBar(binding.tbFav)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val favMovie = database.getAllFavouriteMovie()
        Log.e( "favMovie: ", favMovie.toString())
        if (favMovie.size == 0){
            binding.tvNoFavMovie.visibility = View.VISIBLE
        } else {
            binding.tvNoFavMovie.visibility = View.GONE
        }

        binding.rvItemMovieFavList.apply {
            val listItemMovie = MovieAdapter(favMovie as ArrayList<ResultsData>)
            adapter = listItemMovie
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)

            listItemMovie.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ResultsData) {
                    val detailMovie = Intent(this@FavActivity, DetailActivity::class.java)
                    detailMovie.putExtra(DetailActivity.MOVIE, data)
                    startActivity(detailMovie)
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}