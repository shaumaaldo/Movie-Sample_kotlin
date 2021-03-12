package com.example.samplemovie

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.samplemovie.BuildConfig.API_KEY
import com.example.samplemovie.BuildConfig.BASE_URL
import com.example.samplemovie.data.model.ResponseData
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object NetworkConfig {
    fun getClient(): ApiInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiInterface::class.java)
    }
}

interface ApiInterface {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1
    ): Call<ResponseData>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int = 1
    ): Call<ResponseData>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int = 1
    ): Call<ResponseData>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("language") language: String? = "en-US",
    ): Call<ResponseData>
}
