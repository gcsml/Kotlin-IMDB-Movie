package com.gokhancsml.hilt_retrofit.response

data class MoviesListResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)