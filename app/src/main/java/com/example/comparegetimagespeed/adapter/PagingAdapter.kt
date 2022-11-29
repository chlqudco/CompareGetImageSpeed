package com.example.comparegetimagespeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comparegetimagespeed.databinding.ItemImageBinding

class PagingAdapter: PagingDataAdapter<String, PagingAdapter.ViewHolder>(DiffCallback) {

    var imageNumber = 1

    inner class ViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model : String){
            binding.itemId.text = "$imageNumber"
            imageNumber++

            Glide.with(binding.itemImage)
                .load(model)
                .into(binding.itemImage)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pagedModel = getItem(position)
        pagedModel?.let { model ->
            holder.bind(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return false
            }

        }
    }

}