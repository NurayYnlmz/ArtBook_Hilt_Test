package com.nurayyenilmez.artbookandhiltexample.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.nurayyenilmez.artbookandhiltexample.R
import com.nurayyenilmez.artbookandhiltexample.adapter.ImageRecyclerAdapter
import com.nurayyenilmez.artbookandhiltexample.databinding.FragmentImgApiBinding
import com.nurayyenilmez.artbookandhiltexample.util.Status
import com.nurayyenilmez.artbookandhiltexample.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
):Fragment (R.layout.fragment_img_api) {

    private var viewbinding:FragmentImgApiBinding?=null
    private val ımageApıViewModel : ArtViewModel by activityViewModels()
     // lateinit var ImageApıViewModel : ArtViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      val binding=FragmentImgApiBinding.bind(view)
        viewbinding=binding

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        ımageApıViewModel.searchForImage(it.toString())
                    }
                }
            }
        }


        subscribeToObservers()

        binding.imageRecyclerView.adapter=imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager=GridLayoutManager(requireContext(),3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            ımageApıViewModel.setSelectedImage(it)
        }
    }

    private fun subscribeToObservers() {
        ımageApıViewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->  imageResult.previewURL }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    viewbinding?.progressBar?.visibility = View.GONE

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                  viewbinding?.progressBar?.visibility = View.GONE

                }

                Status.LOADING -> {
                  viewbinding?.progressBar?.visibility = View.VISIBLE

                }
            }

        })
    }

}