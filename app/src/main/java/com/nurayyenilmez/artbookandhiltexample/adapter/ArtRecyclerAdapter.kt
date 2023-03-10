package com.nurayyenilmez.artbookandhiltexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nurayyenilmez.artbookandhiltexample.databinding.ArtRowBinding
import com.nurayyenilmez.artbookandhiltexample.roomdb.Art
import javax.inject.Inject


class ArtRecyclerAdapter @Inject constructor(
    val glide:RequestManager
):RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(val binding: ArtRowBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem

        }
    }
        private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
        var arts: List<Art>
            get() = recyclerListDiffer.currentList
            set(value) = recyclerListDiffer.submitList(value)

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=ArtViewHolder (
       ArtRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

      )


    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val art=arts[position]
        holder.binding.artRowArtNameText.text= "Name: ${art.name}"
        holder.binding.artRowArtistNameText.text="Artist Name: ${art.artistName}"
        holder.binding.artRowYearText.text="Year:${art.year}"
        glide.load(art.ImageUrl).into(holder.binding.artRowImageView)
    }

    override fun getItemCount(): Int {

        return arts.size

    }
}