package com.nurayyenilmez.artbookandhiltexample.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurayyenilmez.artbookandhiltexample.R
import com.nurayyenilmez.artbookandhiltexample.adapter.ArtRecyclerAdapter
import com.nurayyenilmez.artbookandhiltexample.databinding.FragmentArtsBinding
import com.nurayyenilmez.artbookandhiltexample.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor
    (private val artRecyclerAdapter: ArtRecyclerAdapter)
    :Fragment(R.layout.fragment_arts) {

    private var viewBinding:FragmentArtsBinding?=null


      // private val artViewModel: ArtViewModel by activityViewModels()
      lateinit var artViewModel : ArtViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
           return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            artViewModel.deleteArt(selectedArt)
        }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

       artViewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding=FragmentArtsBinding.bind(view)
        viewBinding=binding


        subscribeToObservers()

        binding.artrecyclerView.adapter=artRecyclerAdapter
        binding.artrecyclerView.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.artrecyclerView)

       binding.fab.setOnClickListener {
           findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())

       }

    }

    private fun subscribeToObservers() {
        artViewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })
    }
    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
       viewBinding = null
        super.onDestroyView()
    }
}