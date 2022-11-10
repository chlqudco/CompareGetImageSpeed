package com.example.comparegetimagespeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comparegetimagespeed.databinding.ItemImageBinding
import com.example.comparegetimagespeed.model.UrlModel

class PagingAdapter: PagingDataAdapter<UrlModel, PagingAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model : UrlModel){
            binding.itemId.text = "${model.id}"

            Glide.with(binding.itemImage)
                .load(model.imageUrl)
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
        private val DiffCallback = object : DiffUtil.ItemCallback<UrlModel>(){
            override fun areItemsTheSame(oldItem: UrlModel, newItem: UrlModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UrlModel, newItem: UrlModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}