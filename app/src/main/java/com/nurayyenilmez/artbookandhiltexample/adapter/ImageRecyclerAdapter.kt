package com.nurayyenilmez.artbookandhiltexample.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.RequestManager

import com.nurayyenilmez.artbookandhiltexample.databinding.ImageRowBinding

import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager

) :RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>(){

    class ImageViewHolder(val binding: ImageRowBinding) :RecyclerView.ViewHolder(binding.root)

        private var onItemClickListener : ((String) -> Unit)? = null


        private val diffUtil = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
            }
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
            }
        }
        private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
        var images: List<String>
            get() = recyclerListDiffer.currentList
            set(value) = recyclerListDiffer.submitList(value)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ImageViewHolder (
            ImageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

    fun setOnItemClickListener(listener : (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
         val url =images[position]
        glide.load(url).into(holder.binding.singleArtImageView)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let{
                it(url)
            }
        }

            }





    override fun getItemCount(): Int {
       return images.size
    }


        }
