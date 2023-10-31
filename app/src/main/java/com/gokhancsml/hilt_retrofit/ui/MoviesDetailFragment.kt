package com.gokhancsml.hilt_retrofit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.gokhancsml.hilt_retrofit.R
import com.gokhancsml.hilt_retrofit.databinding.FragmentMoviesDetailBinding
import com.gokhancsml.hilt_retrofit.repository.ApiRepository
import com.gokhancsml.hilt_retrofit.response.MovieDetailResponse
import com.gokhancsml.hilt_retrofit.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesDetailFragment : Fragment() {

    private lateinit var binding: FragmentMoviesDetailBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    private val args : MoviesDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMoviesDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.movieid
        binding.apply {
            prgBarMovies.visibility = View.INVISIBLE
            apiRepository.getMoviesDetails(id).enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>, ) {
                    prgBarMovies.visibility = View.GONE
                    when(response.code()){
                        200 -> {
                            response.body().let {
                                val moviePoster = Constants.POSTER_BASE_URL + it!!.poster_path
                                tvMovieBudget.text= it.budget.toString()
                                tvMovieOverview.text=it.overview
                                tvMovieDateRelease.text=it.release_date
                                tvMovieRating.text=it.vote_average.toString()
                                tvMovieRevenue.text=it.revenue.toString()
                                tvMovieRuntime.text=it.runtime.toString()
                                tvMovieTagLine.text=it.tagline
                                tvMovieTitle.text=it.title

                                imgMovie.load(moviePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgMovieBack.load(moviePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                            }
                        }
                        401 -> {
                            Toast.makeText(requireContext(),"Invalid API key: You must be granted avalid key.",Toast.LENGTH_LONG).show()
                        }
                        404 -> {
                            Toast.makeText(requireContext(),"The resource you requested could not be found.", Toast.LENGTH_LONG).show()

                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    prgBarMovies.visibility=View.GONE
                    Toast.makeText(requireContext(),"onFailure",Toast.LENGTH_LONG).show()
                }
            })

        }

    }

}