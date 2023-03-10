package com.nurayyenilmez.artbookandhiltexample.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("arts")
data class Art(
    val name :String,
    val artistName:String,
    val year:Int,
    val ImageUrl:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null
)
