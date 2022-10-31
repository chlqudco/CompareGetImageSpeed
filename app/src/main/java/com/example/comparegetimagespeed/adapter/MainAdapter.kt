package com.example.comparegetimagespeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comparegetimagespeed.model.UrlModel
import com.example.comparegetimagespeed.databinding.ItemImageBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var urlList: List<UrlModel> = emptyList()

    inner class ViewHolder(
        private val binding: ItemImageBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(model : UrlModel){
            binding.itemId.text = "${model.id}"

            Glide.with(binding.itemImage)
                .load(model.imageUrl)
                .into(binding.itemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(urlList[position])
    }

    override fun getItemCount(): Int {
        return urlList.size
    }
}