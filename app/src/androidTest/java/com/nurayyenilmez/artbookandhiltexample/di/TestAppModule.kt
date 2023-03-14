package com.nurayyenilmez.artbookandhiltexample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import android.content.Context
import androidx.room.Room
import com.nurayyenilmez.artbookandhiltexample.roomdb.ArtDatabase

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java)
            .allowMainThreadQueries()
            .build()

}