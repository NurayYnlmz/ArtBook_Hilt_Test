package com.nurayyenilmez.artbookandhiltexample.view

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.nurayyenilmez.artbookandhiltexample.R
import com.nurayyenilmez.artbookandhiltexample.databinding.FragmentArtsDetailsBinding
import com.nurayyenilmez.artbookandhiltexample.util.Status
import com.nurayyenilmez.artbookandhiltexample.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
) :Fragment(R.layout.fragment_arts_details) {

    private var viewBinding:FragmentArtsDetailsBinding?=null


     // var  detailsViewModel: ArtViewModel by  activityViewModels()

      lateinit var viewModel:ArtViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)


        val binding=FragmentArtsDetailsBinding.bind(view)
         viewBinding=binding

        subscribeToObservers()
        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

          //We write what happens when the back button is pressed.
        val callBack=object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
               findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.saveButton.setOnClickListener {
           viewModel.makeArt(
                binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString())
        }

    }

    private fun subscribeToObservers() {
      viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url->
            viewBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

      viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                   viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }



    override fun onDestroyView() {
        viewBinding=null
        super.onDestroyView()
    }
}