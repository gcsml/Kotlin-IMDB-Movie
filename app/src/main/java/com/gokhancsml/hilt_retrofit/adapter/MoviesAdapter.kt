package com.gokhancsml.hilt_retrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.gokhancsml.hilt_retrofit.R
import com.gokhancsml.hilt_retrofit.databinding.ItemMoviesBinding
import com.gokhancsml.hilt_retrofit.response.MoviesListResponse
import com.gokhancsml.hilt_retrofit.response.Result
import com.gokhancsml.hilt_retrofit.utils.Constants.POSTER_BASE_URL
import javax.inject.Inject

class MoviesAdapter @Inject constructor() : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

   private lateinit var binding: ItemMoviesBinding
   private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

     val inflater = LayoutInflater.from(parent.context)
        binding=ItemMoviesBinding.inflate(inflater,parent,false)
        context=parent.context
        return ViewHolder()

    }

    override fun getItemCount(): Int  = differ.currentList.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        fun set(item : Result){
            binding.apply {
                tvMovieName.text = item.original_title
                tvLang.text = item.original_language
                tvRate.text = item.vote_average.toString()
                tvMovieDateRelease.text = item.release_date
                val moviePoster = POSTER_BASE_URL + item.poster_path
                imgMovie.load(moviePoster){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL )
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }

    }

    private var onItemClickListener : ((Result)-> Unit)?=null

    fun setOnItemClickListener(listener : (Result)-> Unit){
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this,differCallback)

}

