package com.nurayyenilmez.artbookandhiltexample.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat

import com.nurayyenilmez.artbookandhiltexample.MainCoroutineRule
import com.nurayyenilmez.artbookandhiltexample.getOrAwaitValueTest
import com.nurayyenilmez.artbookandhiltexample.repo.FakeArtRepository
import com.nurayyenilmez.artbookandhiltexample.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get: Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule=MainCoroutineRule()


    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        //Test Doubles(kopyası test edeceğiz)

        viewModel= ArtViewModel(FakeArtRepository())
    }

    @Test
    fun ` insert art without year returns error`(){
        viewModel.makeArt("MonaLisa","Da Vinci","")
       val value= viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }
    @Test
    fun ` insert art without name returns error`(){
        viewModel.makeArt("","Da Vinci","1987")
        val value= viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)



    }
    @Test
    fun ` insert art without artistName returns error`(){
        viewModel.makeArt("MonaLisa","","1987")
        val value= viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)


    }


}