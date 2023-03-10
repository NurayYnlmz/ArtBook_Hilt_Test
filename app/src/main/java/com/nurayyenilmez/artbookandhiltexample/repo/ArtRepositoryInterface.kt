package com.nurayyenilmez.artbookandhiltexample.repo

import androidx.lifecycle.LiveData
import com.nurayyenilmez.artbookandhiltexample.model.ImageResponse
import com.nurayyenilmez.artbookandhiltexample.roomdb.Art
import com.nurayyenilmez.artbookandhiltexample.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>

}