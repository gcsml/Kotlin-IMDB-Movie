package com.gokhancsml.hilt_retrofit.api

import com.gokhancsml.hilt_retrofit.response.MovieDetailResponse
import com.gokhancsml.hilt_retrofit.response.MoviesListResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {
    @GET("movie/popular")
    fun getPopularMoviesList(@Query("page") page : Int ) : Call<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")movieId : Int) : Call<MovieDetailResponse>
}