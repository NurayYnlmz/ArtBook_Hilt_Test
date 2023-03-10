package com.nurayyenilmez.artbookandhiltexample.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nurayyenilmez.artbookandhiltexample.R
import com.nurayyenilmez.artbookandhiltexample.api.RetrofitAPI
import com.nurayyenilmez.artbookandhiltexample.repo.ArtRepository
import com.nurayyenilmez.artbookandhiltexample.repo.ArtRepositoryInterface
import com.nurayyenilmez.artbookandhiltexample.roomdb.ArtDao
import com.nurayyenilmez.artbookandhiltexample.roomdb.ArtDatabase
import com.nurayyenilmez.artbookandhiltexample.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context)=Room.databaseBuilder(
        context,ArtDatabase::class.java,"ArtBookDb"
    ).build()


    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase)=database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI():RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)

    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context)=Glide.with(context)
        .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground))

    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api: RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface

    }
