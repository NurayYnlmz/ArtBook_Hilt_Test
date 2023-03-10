package com.nurayyenilmez.artbookandhiltexample.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    //Suspend fun Corouties ile Asynchonous çalışması için kullanıyoruz
    @Delete
    suspend fun deleteArt(art: Art)


    //Live Data zaten Asynchronous çalışır suspend yapamaya gerek yoktur
    @Query("SELECT * FROM arts")
    fun observeArt():LiveData<List<Art>>
}