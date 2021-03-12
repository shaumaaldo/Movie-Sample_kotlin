package com.example.samplemovie.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemovie.utils.FilterBottomSheet
import com.example.samplemovie.NetworkConfig
import com.example.samplemovie.data.model.ResponseData
import com.example.samplemovie.data.model.ResultsData
import com.example.samplemovie.adapter.MovieAdapter
import com.example.samplemovie.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), FilterBottomSheet.BottomSheetMovieType {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()
    }

    private fun initView(){
        title = " New Movie List "
        setSupportActionBar(binding.tbMain)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        getPopularMovies()
    }

    private fun initListener(){
        binding.btnFilter.setOnClickListener {
            FilterBottomSheet().apply {
                show(supportFragmentManager, FilterBottomSheet.TAG)
            }
        }

        binding.ibFav.setOnClickListener{
            val favMovie = Intent(this@MainActivity, FavActivity::class.java)
            startActivity(favMovie)
        }
    }

    private fun getPopularMovies(){
        val call: Call<ResponseData> = NetworkConfig.getClient().getPopularMovies()
        call.enqueue(object : Callback<ResponseData> {

            override fun onResponse(
                call: Call<ResponseData>?,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.results ?: emptyList()
                    Log.e( "onResponse: ", data.toString())
                    binding.rvItemMovieList.apply {
                        val listItemMovie = MovieAdapter(data as ArrayList<ResultsData>)
                        adapter = listItemMovie
                        layoutManager = LinearLayoutManager(applicationContext)
                        setHasFixedSize(true)

                        listItemMovie.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ResultsData) {
                                val detailMovie = Intent(this@MainActivity, DetailActivity::class.java)
                                detailMovie.putExtra(DetailActivity.MOVIE, data)
                                startActivity(detailMovie)
                            }
                        })
                    }

                } else {
                    Log.e( "error: ", "error")
                }
            }

            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                Log.e( "call: ", call.toString())
                Log.e( "Throwable: ", t.toString())
            }

        })
    }

    private fun getUpcomingMovies(){
        val call: Call<ResponseData> = NetworkConfig.getClient().getUpcomingMovies()
        call.enqueue(object : Callback<ResponseData> {

            override fun onResponse(
                call: Call<ResponseData>?,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.results ?: emptyList()
                    Log.e( "onResponse: ", data.toString())
                    binding.rvItemMovieList.apply {
                        val listItemMovie = MovieAdapter(data as ArrayList<ResultsData>)
                        adapter = listItemMovie
                        layoutManager = LinearLayoutManager(applicationContext)
                        setHasFixedSize(true)

                        listItemMovie.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ResultsData) {
                                val detailMovie = Intent(this@MainActivity, DetailActivity::class.java)
                                detailMovie.putExtra(DetailActivity.MOVIE, data)
                                startActivity(detailMovie)
                            }
                        })
                    }

                } else {
                    Log.e( "error: ", "error")
                }
            }

            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                Log.e( "call: ", call.toString())
                Log.e( "Throwable: ", t.toString())
            }

        })
    }

    private fun getTopPopularMovies(){
        val call: Call<ResponseData> = NetworkConfig.getClient().getTopRatedMovies()
        call.enqueue(object : Callback<ResponseData> {

            override fun onResponse(
                call: Call<ResponseData>?,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.results ?: emptyList()
                    Log.e( "onResponse: ", data.toString())
                    binding.rvItemMovieList.apply {
                        val listItemMovie = MovieAdapter(data as ArrayList<ResultsData>)
                        adapter = listItemMovie
                        layoutManager = LinearLayoutManager(applicationContext)
                        setHasFixedSize(true)

                        listItemMovie.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ResultsData) {
                                val detailMovie = Intent(this@MainActivity, DetailActivity::class.java)
                                detailMovie.putExtra(DetailActivity.MOVIE, data)
                                startActivity(detailMovie)
                            }
                        })
                    }

                } else {
                    Log.e( "error: ", "error")
                }
            }

            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                Log.e( "call: ", call.toString())
                Log.e( "Throwable: ", t.toString())
            }

        })
    }

    private fun getNowPlayingMovies(){
        val call: Call<ResponseData> = NetworkConfig.getClient().getNowPlayingMovies()
        call.enqueue(object : Callback<ResponseData> {

            override fun onResponse(
                call: Call<ResponseData>?,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.results ?: emptyList()
                    Log.e( "onResponse: ", data.toString())
                    binding.rvItemMovieList.apply {
                        val listItemMovie = MovieAdapter(data as ArrayList<ResultsData>)
                        adapter = listItemMovie
                        layoutManager = LinearLayoutManager(applicationContext)
                        setHasFixedSize(true)

                        listItemMovie.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ResultsData) {
                                val detailMovie = Intent(this@MainActivity, DetailActivity::class.java)
                                detailMovie.putExtra(DetailActivity.MOVIE, data)
                                startActivity(detailMovie)
                            }
                        })
                    }

                } else {
                    Log.e( "error: ", "error")
                }
            }

            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                Log.e( "call: ", call.toString())
                Log.e( "Throwable: ", t.toString())
            }

        })
    }

    override fun onTypeClicked(type: String) {
        when (type) {
            "Popular" -> {
                getPopularMovies()
            }
            "Upcoming" -> {
                getUpcomingMovies()
            }
            "Top Rated" -> {
                getTopPopularMovies()
            }
            else -> {
                getNowPlayingMovies()
            }
        }
    }


}