package com.gokhancsml.hilt_retrofit.repository

import com.gokhancsml.hilt_retrofit.api.ApiServices
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiServices: ApiServices
) {
    fun getPopularMoviesList(page : Int) = apiServices.getPopularMoviesList(page)

    fun getMoviesDetails(id : Int) = apiServices.getMovieDetails(id)



}