package com.nurayyenilmez.artbookandhiltexample.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.nurayyenilmez.artbookandhiltexample.adapter.ArtRecyclerAdapter
import com.nurayyenilmez.artbookandhiltexample.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide:RequestManager,
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) :FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {


        return when(className){
            ImageApiFragment::class.java.name->ImageApiFragment(imageRecyclerAdapter)
            ArtFragment::class.java.name->ArtFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name->ArtDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }


    }
}